# Estrutura de Conteúdo para Apresentação
## ToDo App - Firebase Authentication & Jetpack Compose

---

## SLIDE 1: Visão Geral do Projeto

### Título
**ToDo App com Firebase & Jetpack Compose**

### Conteúdo

**Desenvolvedores:**
- Victor Hugo Monteiro
- Murilo Melo

**Tecnologias Principais:**
- Kotlin + Jetpack Compose (UI 100% declarativa)
- Firebase Authentication (autenticação de usuários)
- Cloud Firestore (persistência em tempo real)
- Hilt/Dagger (injeção de dependências)
- Arquitetura MVVM

**Funcionalidades Implementadas:**
- ✅ Login e cadastro de usuários
- ✅ Criar, listar, marcar e excluir tarefas
- ✅ Sincronização em tempo real entre dispositivos
- ✅ Persistência por usuário (dados isolados)
- ✅ Interface Material Design 3

**Estrutura de Navegação:**
```
Login → Cadastro
  ↓
Home (Lista de Tarefas)
  - FloatingActionButton (+) → Dialog Adicionar Tarefa
  - Botão Logout → Volta para Login
```

### Elementos Visuais
- Logo/ícone do app
- Screenshot das 3 telas principais (Login, Cadastro, Lista)
- Diagrama simples de fluxo

### Notas para Apresentação
- Falar sobre o contexto: "Reescrita do trabalho 1 com backend na nuvem"
- Mencionar objetivo: "Aprender integração cloud e autenticação"
- Tempo: ~2 minutos

---

## SLIDE 2: Arquitetura e Decisões Técnicas

### Título
**Arquitetura MVVM e Padrões Implementados**

### Conteúdo

**Diagrama de Arquitetura:**
```
┌─────────────────────────────┐
│   UI Layer (Compose)        │
│   LoginScreen, ListScreen   │
└────────────┬────────────────┘
             │ observa StateFlow
┌────────────▼────────────────┐
│   ViewModel                 │
│   AuthViewModel, TaskVM     │
└────────────┬────────────────┘
             │ chama
┌────────────▼────────────────┐
│   Repository (Interface)    │
│   AuthRepo, TaskRepo        │
└────────────┬────────────────┘
             │ acessa
┌────────────▼────────────────┐
│   Firebase (Auth+Firestore) │
└─────────────────────────────┘
```

**Decisões Técnicas Importantes:**

1. **Sealed Class para Estados**
```kotlin
sealed class Response<out T> {
    object Loading : Response<Nothing>()
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val message: String) : Response<Nothing>()
}
```
- Garante tratamento de todos os estados
- Type-safe: compilador força exhaustividade

2. **Flow para Tempo Real**
```kotlin
fun getTasks(): Flow<Response<List<Task>>> = callbackFlow {
    val subscription = tasksCollection
        .whereEqualTo("userId", userId)
        .addSnapshotListener { snapshot, error ->
            // Emite automaticamente quando dados mudam
            trySend(Response.Success(tasks))
        }
    awaitClose { subscription.remove() }
}
```
- Sincronização automática
- Suporte multi-dispositivo
- Gerenciamento de lifecycle

3. **Injeção de Dependências com Hilt**
```kotlin
@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repo: TaskRepository
) : ViewModel()
```
- Facilita testes
- Reduz acoplamento
- Lifecycle-aware

**Estrutura de Pacotes:**
```
data/
  - model/Task.kt
  - repository/AuthRepository, TaskRepository
  - Response.kt
di/AppModule.kt (Hilt)
ui/
  - auth/LoginScreen, SignUpScreen, AuthViewModel
  - task/ListScreen, TaskViewModel
  - components/TaskItem, CustomButton
  - navigation/Routes, AppNavHost
```

### Elementos Visuais
- Diagrama de camadas colorido
- Snippets de código (TAMANHO LEGÍVEL: fonte 14-16pt mínimo)
- Ícones representando cada camada

### Notas para Apresentação
- Explicar por que MVVM: "Separação de responsabilidades, testabilidade"
- Destacar Flow: "UI atualiza sozinha quando dados mudam no Firestore"
- Mencionar facilidade de testes com Repository Pattern
- Tempo: ~3 minutos

---

## SLIDE 3: Dificuldades Encontradas e Soluções

### Título
**Desafios e Aprendizados**

### Conteúdo

**1. Checkbox não Atualizava Estado**

**Problema:**
- Usuário marcava tarefa como concluída, mas a UI não refletia a mudança imediatamente

**Causa Raiz:**
```kotlin
// ❌ ERRADO - toObjects() reutilizava referências
val tasks = snapshot.toObjects(Task::class.java)
// Compose não detectava mudança, não recompunha
```

**Solução:**
```kotlin
// ✅ CORRETO - Mapeamento manual cria novos objetos
val tasks = snapshot.documents.map { doc ->
    Task(
        id = doc.id,
        title = doc.getString("title") ?: "",
        description = doc.getString("description") ?: "",
        isCompleted = doc.getBoolean("isCompleted") ?: false,
        userId = doc.getString("userId") ?: ""
    )
}
trySend(Response.Success(tasks))
```

**Aprendizado:** O Compose precisa de novas instâncias de objetos para detectar mudanças e acionar recomposição. Mapear manualmente garante que cada snapshot emita novos objetos no Flow.

---

**2. Tarefas de Outros Usuários Apareciam**

**Problema:**
- Ao fazer login, usuário via tarefas de todos os usuários do sistema

**Causa Raiz:**
```kotlin
// ❌ Sem filtro de usuário
tasksCollection.addSnapshotListener { ... }
```

**Solução:**
```kotlin
// ✅ Filtro por userId
tasksCollection
    .whereEqualTo("userId", auth.currentUser?.uid)
    .addSnapshotListener { ... }
```

**Aprendizado:** Importância de filtros no Firestore + regras de segurança adequadas

---

**3. Crash ao Adicionar Tarefa sem Login**

**Problema:**
- App crashava se usuário tentasse adicionar tarefa com sessão expirada

**Causa Raiz:**
```kotlin
// ❌ Sem verificação de null
val userId = auth.currentUser?.uid
// NullPointerException se usuário não logado
```

**Solução:**
```kotlin
// ✅ Verificação explícita
val userId = auth.currentUser?.uid ?: throw Exception("Usuário não logado")
// Ou early return:
if (userId.isEmpty()) {
    return Response.Error("Usuário não autenticado")
}
```

**Aprendizado:** Sempre validar estado de autenticação antes de operações críticas

---

**4. Performance com Muitas Tarefas**

**Problema Identificado:**
- Com mais de 50 tarefas, lista começava a ficar lenta

**Solução Futura:**
- Implementar paginação com Paging 3 Library
- Adicionar índices no Firestore para queries mais rápidas
- Cache local com Room para operação offline

### Elementos Visuais
- Ícones de "erro" e "sucesso" para cada problema/solução
- Código com destaque (antes/depois)
- Emoji/ícone de "lâmpada" para aprendizados

### Notas para Apresentação
- Ser honesto sobre dificuldades: "Mostra processo real de desenvolvimento"
- Destacar que erros são oportunidades de aprendizado
- Mencionar importância de debugging sistemático
- Tempo: ~3 minutos

---

## SLIDE 4: Uso de LLMs no Desenvolvimento

### Título
**Inteligência Artificial no Processo de Desenvolvimento**

### Conteúdo

**LLMs Utilizadas:**
1. **Google Gemini** (Principal)
2. **Claude (Anthropic)** (Documentação e revisão)

---

**Exemplos de Prompts Importantes:**

**1. Estruturação Inicial do Projeto**
```
Prompt: "Crie uma interface AuthRepository com funções 
suspensas para login, cadastro, logout e uma propriedade 
para obter o usuário atual (currentUser). As funções devem 
retornar a classe Response."
```
- Resultado: Interface limpa e seguindo padrão Kotlin
- Tempo economizado: ~30 minutos de design

**2. Implementação de Repository**
```
Prompt: "Implemente a classe AuthRepositoryImpl que herda 
de AuthRepository. Use @Inject para receber o FirebaseAuth. 
Implemente os métodos login e signUp usando await() para 
converter as tarefas do Firebase em coroutines. Trate 
exceções retornando Response.Error."
```
- Resultado: Código production-ready com tratamento de erros
- Aprendizado: Como usar `await()` com Firebase Tasks

**3. Correção de Bugs**
```
Prompt: "A checkbox da tarefa não está atualizando o estado 
no Firestore. Estou usando task.copy(isCompleted = isChecked) 
e salvando com .set(). O que pode estar errado?"
```
- Resultado: Identificação do problema (set vs update)
- Diagnóstico rápido que teria levado horas de debugging

**4. UI Components**
```
Prompt: "Crie um componente Composable reutilizável chamado 
CustomButton. Ele deve receber: text, onClick, modifier, 
isLoading e enabled. Se isLoading for true, mostre um 
CircularProgressIndicator pequeno dentro do botão."
```
- Resultado: Componente reutilizável e consistente
- Reutilizado em 3+ telas

---

**Estatísticas de Uso:**

| Fase do Projeto           | % Uso LLM | Atividade                                |
|---------------------------|-----------|------------------------------------------|
| Setup Inicial             | 60%       | Estrutura de pastas, configuração Hilt   |
| Implementação Repository  | 70%       | Código base com padrões corretos         |
| UI/Compose                | 40%       | Componentes base, ajustes manuais depois |
| Debug                     | 50%       | Identificação de problemas               |
| Documentação              | 80%       | README, docs técnicas                    |

---

**Opinião Geral do Grupo sobre LLMs:**

**Pontos Positivos:**
✅ **Aceleração**: Redução de 40-50% no tempo de desenvolvimento
✅ **Aprendizado**: Exposição a boas práticas (coroutines, sealed classes, etc.)
✅ **Qualidade**: Código gerado segue padrões da comunidade
✅ **Documentação**: Comentários e documentação gerados automaticamente
✅ **Debugging**: Identificação rápida de problemas comuns

**Pontos de Atenção:**
⚠️ **Validação Necessária**: Nem todo código gerado está correto
⚠️ **Compreensão**: Importante entender o que a LLM gerou, não só copiar
⚠️ **Contexto Limitado**: LLMs não conhecem o projeto completo
⚠️ **Dependência**: Risco de não desenvolver habilidade de resolver problemas sozinho

**Estratégia Recomendada:**
1. Use LLM para estrutura inicial e boilerplate
2. Revise e entenda cada linha gerada
3. Faça modificações manuais baseadas em necessidades específicas
4. Use LLM para debugging, mas confirme o diagnóstico
5. Sempre teste o código gerado antes de integrar

---

**Conclusão:**
LLMs são ferramentas poderosas que **aceleram** desenvolvimento, mas não **substituem** compreensão profunda. O equilíbrio ideal é usar LLMs como "assistentes de programação" enquanto mantemos pensamento crítico e validação rigorosa.

### Elementos Visuais
- Logos das LLMs utilizadas
- Gráfico/tabela de estatísticas de uso
- Ícones de "positivo" e "atenção"
- Screenshot de prompt bem-sucedido

### Notas para Apresentação
- Ser transparente sobre uso de LLMs (política da disciplina)
- Enfatizar que aprendizado foi mantido
- Destacar importância de validação
- Mencionar que LLMs são o futuro do desenvolvimento
- Tempo: ~3 minutos

---

## DEMONSTRAÇÃO AO VIVO (Não é slide, mas parte da apresentação)

### Roteiro de Demo

**No Smartphone:**

1. **Tela de Login** (mostrar erro de credencial inválida)
   - Tentar login com credencial errada → Toast de erro

2. **Ir para Cadastro**
   - Mostrar validação de senhas diferentes
   - Criar conta nova com sucesso

3. **Tela Principal (Lista)**
   - Mostrar lista vazia com mensagem amigável
   - Adicionar 2-3 tarefas rapidamente
   - Marcar uma como concluída (checkbox)
   - Excluir uma tarefa

4. **Demonstrar Tempo Real** (Se possível)
   - Abrir emulador simultaneamente com mesma conta
   - Fazer alteração no celular → Mostrar que aparece no emulador

5. **Logout**
   - Fazer logout
   - Fazer login novamente
   - Mostrar que tarefas persistiram

**No Emulador (Se tempo permitir):**
- Executar os mesmos passos para reforçar funcionamento
- Mostrar responsividade em diferentes tamanhos de tela

### Tempo: ~3-4 minutos

---

## RESUMO DE TIMING DA APRESENTAÇÃO

| Seção                    | Tempo    | Responsável Sugerido |
|--------------------------|----------|----------------------|
| Slide 1: Visão Geral     | 2 min    | [Membro 1]           |
| Slide 2: Arquitetura     | 3 min    | [Membro 2]           |
| Slide 3: Dificuldades    | 3 min    | [Membro 1]           |
| Slide 4: LLMs            | 3 min    | [Membro 2]           |
| Demonstração Live        | 3-4 min  | [Ambos]              |
| **TOTAL**                | **14-15min** |                  |

---

## DICAS IMPORTANTES PARA APRESENTAÇÃO

### Antes da Apresentação
- [ ] Testar app em smartphone e emulador
- [ ] Carregar baterias completamente
- [ ] Ter backup de internet (hotspot celular)
- [ ] Slides em PDF (para compatibilidade)
- [ ] Instalar app de espelhamento de tela (scrcpy, Vysor)
- [ ] Limpar tarefas antigas do Firebase (começar com lista vazia)

### Durante a Apresentação
- [ ] Falar alto e claro
- [ ] Fazer contato visual com a turma
- [ ] Não ler os slides (usar como apoio)
- [ ] Mostrar entusiasmo pelo projeto
- [ ] Responder perguntas com calma e objetividade
- [ ] Se algo der errado, manter a calma e ter plano B

### Plano B para Problemas Técnicos
- **Internet cai**: Mostrar vídeo gravado previamente
- **App crasha**: Ter APK backup instalado
- **Emulador não inicia**: Focar na demo no smartphone
- **Smartphone descarrega**: Usar apenas emulador

---

## MATERIAIS A PREPARAR

### Para Apresentação
1. ✅ Slides em PowerPoint/Google Slides (exportar PDF)
2. ✅ App instalado e funcionando em smartphone
3. ✅ Emulador configurado e testado
4. ✅ Vídeo de backup (se possível)
5. ✅ Cabo USB e adaptadores necessários

### Para Entrega
1. ✅ Código no GitHub (repositório público)
2. ✅ README.md completo
3. ✅ Documentação técnica (PDF do LaTeX)
4. ✅ Slides da apresentação (PDF)
5. ✅ google-services.json (NÃO incluir no Git - adicionar .gitignore)

---

## CHECKLIST FINAL

- [ ] Código compila sem erros
- [ ] App funciona em smartphone físico
- [ ] App funciona em emulador
- [ ] Todos os requisitos do trabalho foram implementados
- [ ] README está completo e formatado
- [ ] Documento técnico está em LaTeX e compilado para PDF
- [ ] Slides têm no máximo 4 slides
- [ ] Códigos nos slides estão legíveis (fonte ≥ 14pt)
- [ ] Repositório GitHub está público e organizado
- [ ] .gitignore está configurado (google-services.json, build/, etc.)
- [ ] Ambos os membros sabem apresentar todas as partes
- [ ] Ensaio da apresentação foi realizado (timing!)

---

**Boa sorte na apresentação! 🚀**
