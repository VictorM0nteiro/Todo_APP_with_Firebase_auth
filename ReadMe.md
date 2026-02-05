# 📝 ToDo App - Firebase & Jetpack Compose

Um aplicativo de gerenciamento de tarefas moderno, construído nativamente para Android utilizando **Kotlin**, **Jetpack Compose** e **Firebase** como backend. O projeto foca em arquitetura limpa, injeção de dependência e gerenciamento de estado reativo.

## 🚀 Funcionalidades

* **Autenticação de Usuário:**
    * Login e Cadastro via E-mail e Senha (Firebase Authentication).
    * Tratamento de erros de segurança e validação de input.
* **Gerenciamento de Tarefas:**
    * Criação de novas tarefas.
    * Listagem de tarefas em tempo real.
    * Marcação de tarefas como concluídas (Checkbox).
    * Exclusão de tarefas.
* **Interface Reativa:** UI construída 100% em Kotlin com Jetpack Compose, reagindo a mudanças de estado instantaneamente.

## 🏗️ Decisões de Arquitetura

O projeto segue a arquitetura **MVVM (Model-View-ViewModel)** combinada com o padrão de **Clean Architecture** simplificado para manter o código testável e organizado.

### 1. MVVM (Model-View-ViewModel)
* **View (Compose):** Responsável apenas por desenhar a tela. Não contém lógica de negócios. Observa os dados expostos pela ViewModel.
* **ViewModel:** Gerencia o estado da UI (`StateFlow` / `LiveData`) e comunica-se com a camada de dados (Repositórios). Sobrevive a mudanças de configuração (rotação de tela).
* **Model:** Representa os dados (ex: Data Class `TodoTask`).

### 2. Injeção de Dependência (Hilt)
Utilizamos **Hilt (Dagger)** para gerenciar a criação de objetos.
* **Por que?** Evita acoplamento forte entre classes e facilita a manutenção. Por exemplo, a `MainActivity` não precisa saber como criar uma instância do `AuthRepository`; o Hilt injeta isso automaticamente com `@AndroidEntryPoint`.

### 3. Padrão de Repositório (Repository Pattern)
Uma camada de abstração entre a ViewModel e o Firebase.
* **Decisão:** Se no futuro decidirmos trocar o Firebase por um banco de dados local (Room) ou uma API REST, só precisamos alterar o Repositório, sem quebrar as telas do app.

### 4. Navegação
Uso do **Navigation Compose** para gerenciar o fluxo entre telas (Login -> Home), mantendo o conceito de "Single Activity Application".

## 🛠️ Tech Stack & Bibliotecas

* **Linguagem:** [Kotlin](https://kotlinlang.org/)
* **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
    * Material Design 3
* **Injeção de Dependência:** [Hilt](https://dagger.dev/hilt/)
* **Backend as a Service:** [Firebase](https://firebase.google.com/)
    * Authentication (Auth)
    * Firestore / Realtime Database (DB)
* **Assincronismo:** Coroutines & Flow

## 📱 Telas do Aplicativo

### 1. Tela de Login / Cadastro
* Foco em UX simples.
* Feedback visual de erros (ex: senha fraca, e-mail inválido).
* Integração direta com Firebase Auth.

### 2. Tela Principal (Home)
* Exibição da lista de tarefas.
* Input para adicionar novos itens rapidamente.
* Interatividade imediata (check/uncheck) refletindo no banco de dados.

## ⚙️ Como rodar o projeto

1.  Clone este repositório:
    ```bash
    git clone https://github.com/VictorM0nteiro/Todo_APP_with_Firebase_auth.git
    ```
2.  Abra o projeto no **Android Studio**.
3.  **Configuração do Firebase:**
    * Crie um projeto no console do Firebase.
    * Baixe o arquivo `google-services.json`.
    * Coloque o arquivo na pasta `app/` do projeto.
4.  Compile e execute em um emulador ou dispositivo físico.

---
**Desenvolvido por Victor Hugo Monteiro e Murilo Melo**

**ReadMe desenvolvido utilizando GEMINI**