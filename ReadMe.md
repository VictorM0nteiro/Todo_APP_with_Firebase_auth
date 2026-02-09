# 📝 ToDo App - Firebase & Jetpack Compose

## 👥 Desenvolvedores
- **Victor Hugo Monteiro**
- **Murilo Melo**

---

## 📋 Sobre o Projeto

Aplicativo Android de gerenciamento de tarefas desenvolvido em **Kotlin** utilizando **Jetpack Compose** para interface moderna e **Firebase** como backend na nuvem. O projeto implementa autenticação de usuários, persistência de dados em tempo real e arquitetura MVVM com injeção de dependências.

**Disciplina:** Programação para Dispositivos Móveis  
**Instituição:** Universidade Federal de Uberlândia (UFU)  
**Data:** Fevereiro de 2026

---

## 🎯 Funcionalidades Implementadas

### ✅ Autenticação de Usuário
- **Login** com e-mail e senha via Firebase Authentication
- **Cadastro** de novos usuários com validação de dados
- **Logout** seguro com limpeza de sessão
- Verificação automática de sessão ativa ao abrir o app
- Tratamento de erros com feedback visual (Toast)

### ✅ Gerenciamento de Tarefas
- **Criação** de tarefas com título e descrição opcional
- **Listagem** de tarefas em tempo real (sincronização automática)
- **Marcação** de tarefas como concluídas via checkbox interativa
- **Exclusão** de tarefas com confirmação visual
- **Persistência** por usuário (cada usuário vê apenas suas tarefas)

### ✅ Interface de Usuário
- Design moderno seguindo **Material Design 3**
- Interface 100% em **Jetpack Compose**
- Responsiva e adaptável a diferentes tamanhos de tela
- Estados visuais (loading, vazio, erro) claramente definidos
- Animações suaves e feedback tátil

---

## 🏗️ Arquitetura e Organização

### Padrão MVVM (Model-View-ViewModel)

O projeto segue a arquitetura **MVVM** combinada com princípios de **Clean Architecture**:

```
┌─────────────────────────────────────────────┐
│              UI Layer (View)                │
│   - LoginScreen / SignUpScreen              │
│   - ListScreen / TaskItem                   │
│   - Componentes reutilizáveis               │
└──────────────┬──────────────────────────────┘
               │ observa StateFlow/LiveData
┌──────────────▼──────────────────────────────┐
│         ViewModel Layer                     │
│   - AuthViewModel                           │
│   - TaskViewModel                           │
│   (Gerencia estado e lógica de UI)          │
└──────────────┬──────────────────────────────┘
               │ chama métodos
┌──────────────▼──────────────────────────────┐
│         Repository Layer                    │
│   - AuthRepository / AuthRepositoryImpl     │
│   - TaskRepository / TaskRepositoryImpl     │
│   (Abstração da fonte de dados)             │
└──────────────┬──────────────────────────────┘
               │ acessa
┌──────────────▼──────────────────────────────┐
│         Data Source (Firebase)              │
│   - Firebase Authentication                 │
│   - Cloud Firestore                         │
└─────────────────────────────────────────────┘
```

### Estrutura de Pacotes

```
com.example.todoapp_firebase/
├── data/
│   ├── model/
│   │   └── Task.kt                    # Data class da tarefa
│   ├── repository/
│   │   ├── AuthRepository.kt          # Interface de autenticação
│   │   ├── AuthRepositoryImpl.kt      # Implementação Firebase Auth
│   │   ├── TaskRepository.kt          # Interface de tarefas
│   │   └── TaskRepositoryImpl.kt      # Implementação Firestore
│   └── Response.kt                    # Sealed class para estados
├── di/
│   └── AppModule.kt                   # Módulo Hilt (DI)
├── ui/
│   ├── auth/
│   │   ├── AuthViewModel.kt           # ViewModel de autenticação
│   │   ├── LoginScreen.kt             # Tela de login
│   │   └── SignUpScreen.kt            # Tela de cadastro
│   ├── task/
│   │   ├── TaskViewModel.kt           # ViewModel de tarefas
│   │   ├── ListScreen.kt              # Lista de tarefas
│   │   └── AddTaskDialog.kt           # Diálogo para adicionar
│   ├── components/
│   │   ├── CustomButton.kt            # Botão reutilizável
│   │   ├── CustomTextField.kt         # Campo de texto customizado
│   │   └── TaskItem.kt                # Item da lista
│   ├── navigation/
│   │   ├── Routes.kt                  # Definição de rotas
│   │   └── AppNavHost.kt              # Configuração de navegação
│   └── theme/
│       ├── Color.kt                   # Paleta de cores
│       ├── Theme.kt                   # Tema Material3
│       └── Type.kt                    # Tipografia
├── util/
│   └── Constants.kt                   # Constantes do app
├── MainActivity.kt                    # Activity principal
└── TodoApplication.kt                 # Classe Application (Hilt)
```

---

## 🔧 Tecnologias e Bibliotecas

### Core
- **Kotlin** 1.9+ - Linguagem moderna e concisa
- **Jetpack Compose** - Toolkit declarativo de UI
- **Material Design 3** - Sistema de design do Google

### Firebase
- **Firebase Authentication** - Autenticação de usuários
- **Cloud Firestore** - Banco de dados NoSQL em tempo real
- **Firebase SDK** 32.7.0

### Arquitetura e Injeção de Dependências
- **Hilt (Dagger)** - Injeção de dependências
- **ViewModel** - Gerenciamento de estado com ciclo de vida
- **Navigation Compose** - Navegação entre telas

### Concorrência
- **Kotlin Coroutines** - Programação assíncrona
- **Flow** - Stream de dados reativo

---

## 🎨 Telas do Aplicativo

### 1. Tela de Login
- Campos: E-mail e Senha
- Validação de entrada em tempo real
- Botão "Entrar" com loading indicator
- Link para tela de cadastro
- Feedback de erros (e-mail inválido, senha incorreta, etc.)

### 2. Tela de Cadastro
- Campos: E-mail, Senha e Confirmar Senha
- Validação de senhas correspondentes
- Requisitos de senha segura
- Botão "Cadastrar" com loading
- Link para voltar ao login

### 3. Tela Principal (Lista de Tarefas)
- AppBar com título e botão de logout
- Lista de tarefas com scroll
- FloatingActionButton (+) para adicionar tarefa
- Cada tarefa mostra:
    - Checkbox interativa
    - Título e descrição
    - Botão de excluir
- Estado vazio com mensagem amigável
- Estado de loading com indicador
- Tratamento de erros

### 4. Diálogo de Nova Tarefa
- Campos: Título (obrigatório) e Descrição (opcional)
- Validação de título não vazio
- Botões "Adicionar" e "Cancelar"

---

## 🔑 Decisões de Arquitetura

### 1. Por que MVVM?
- **Separação de responsabilidades**: UI não contém lógica de negócios
- **Testabilidade**: ViewModels podem ser testadas sem UI
- **Lifecycle-aware**: ViewModels sobrevivem a mudanças de configuração
- **Reatividade**: StateFlow permite UI reativa a mudanças de estado

### 2. Por que Hilt (Dagger)?
- **Reduz boilerplate**: Menos código manual para DI
- **Compile-time safety**: Erros detectados em tempo de compilação
- **Integração Android**: Suporte nativo para ViewModels e Activities
- **Manutenibilidade**: Facilita troca de implementações

### 3. Por que Repository Pattern?
- **Abstração da fonte de dados**: UI não sabe se usa Firebase, Room ou API
- **Single Source of Truth**: Centraliza lógica de acesso a dados
- **Facilita testes**: Pode mockar repositórios facilmente
- **Flexibilidade**: Trocar Firebase por outra solução requer mudar apenas o Repository

### 4. Por que Sealed Class Response?
```kotlin
sealed class Response<out T> {
    object Loading : Response<Nothing>()
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val message: String) : Response<Nothing>()
}
```
- **Type-safe**: Força tratamento de todos os estados
- **Expressiva**: Código mais legível que booleanos
- **Exhaustive**: Compilador garante que todos os casos são tratados

### 5. Por que Flow para Firestore?
- **Atualizações em tempo real**: Sincronização automática entre dispositivos
- **Reatividade**: UI atualiza automaticamente quando dados mudam
- **Gerenciamento de ciclo de vida**: `callbackFlow` com `awaitClose`
- **Backpressure**: Flow gerencia fluxo de dados automaticamente

---

## 🔥 Configuração do Firebase

### Passo 1: Criar Projeto no Firebase Console
1. Acesse [Firebase Console](https://console.firebase.google.com)
2. Crie um novo projeto
3. Adicione um app Android com package name: `com.example.todoapp_firebase`

### Passo 2: Baixar google-services.json
1. No console do Firebase, vá em Configurações do Projeto
2. Baixe o arquivo `google-services.json`
3. Coloque na pasta `app/` do projeto Android

### Passo 3: Ativar Serviços
1. **Authentication**: Ative "E-mail/senha" nos métodos de login
2. **Firestore**: Crie banco de dados no modo teste
3. Configure regras de segurança:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos
- Android Studio Hedgehog (2023.1.1) ou superior
- JDK 17 ou superior
- Dispositivo Android ou Emulador com API 24+ (Android 7.0+)

### Passo a Passo

1. **Clone o repositório**
```bash
git clone https://github.com/VictorM0nteiro/Todo_APP_with_Firebase_auth.git
cd Todo_APP_with_Firebase_auth
```

2. **Configure o Firebase**
    - Coloque o arquivo `google-services.json` na pasta `app/`
    - Certifique-se que Authentication e Firestore estão ativos

3. **Abra no Android Studio**
    - File → Open → Selecione a pasta do projeto
    - Aguarde sincronização do Gradle

4. **Execute o aplicativo**
    - Conecte um dispositivo físico via USB (com depuração USB ativa) OU
    - Inicie um emulador Android
    - Clique em "Run" (Shift + F10)

5. **Teste o aplicativo**
    - Crie uma conta na tela de cadastro
    - Faça login
    - Adicione, marque e exclua tarefas
    - Faça logout e login novamente para verificar persistência

---

## 🚀 Melhorias Futuras

### Curto Prazo
- [ ] Edição de tarefas existentes
- [ ] Data de vencimento para tarefas
- [ ] Prioridades (Alta, Média, Baixa)
- [ ] Filtros (Todas, Ativas, Concluídas)
- [ ] Ordenação (Data, Prioridade, Alfabética)

### Médio Prazo
- [ ] Categorias/Tags personalizáveis
- [ ] Busca de tarefas
- [ ] Notificações push para lembretes
- [ ] Tema escuro/claro (toggle manual)
- [ ] Sincronização offline com Room
- [ ] Animações de transição entre telas

### Longo Prazo
- [ ] Compartilhamento de tarefas entre usuários
- [ ] Subtarefas (tarefas hierárquicas)
- [ ] Anexos (fotos, arquivos)
- [ ] Estatísticas de produtividade
- [ ] Integração com Google Calendar
- [ ] Widget para tela inicial

---

## 🐛 Problemas Conhecidos e Soluções

### Problema: Checkbox não atualiza estado
**Causa**: Uso de `.set()` ao invés de `.update()` no Firestore  
**Solução**: Implementado `updateTask()` usando `.update()` com campos específicos

### Problema: Crash ao adicionar tarefa sem usuário logado
**Causa**: `auth.currentUser` null não tratado  
**Solução**: Verificação de `userId.isEmpty()` com early return

---

## 📚 Referências

- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Firebase Android Setup](https://firebase.google.com/docs/android/setup)
- [Hilt Dependency Injection](https://developer.android.com/training/dependency-injection/hilt-android)
- [Material Design 3](https://m3.material.io/)
- [Kotlin Coroutines Guide](https://kotlinlang.org/docs/coroutines-guide.html)
- [Video Tutorial: Firebase Auth com Compose](https://www.youtube.com/watch?v=KOnLpNZ4AFc)

---

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos como parte da disciplina de Programação para Dispositivos Móveis da UFU.

---

## 🤝 Contribuições

Desenvolvido por **Victor Hugo Monteiro** e **Murilo Melo** utilizando assistência de LLMs (Google Gemini) para aceleração de desenvolvimento e aprendizado de boas práticas.

**README desenvolvido com assistência de Claude (Anthropic)**

---

**Universidade Federal de Uberlândia - 2026**