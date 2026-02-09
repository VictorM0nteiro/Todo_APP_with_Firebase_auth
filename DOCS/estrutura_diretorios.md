# Estrutura de Diretórios do Projeto TodoApp Firebase

## Estrutura Simplificada (Arquivos Essenciais)

```
TodoApp_firebase/
│
├── app/
│   ├── google-services.json              # Configuração Firebase (NÃO commitar no Git!)
│   ├── build.gradle                      # Dependências do app
│   │
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml       # Manifest do app
│           │
│           ├── java/com/example/todoapp_firebase/
│           │   │
│           │   ├── MainActivity.kt       # Activity principal
│           │   ├── TodoApplication.kt    # Application class (Hilt)
│           │   │
│           │   ├── data/
│           │   │   ├── model/
│           │   │   │   └── Task.kt       # Data class da tarefa
│           │   │   │
│           │   │   ├── repository/
│           │   │   │   ├── AuthRepository.kt        # Interface autenticação
│           │   │   │   ├── AuthRepositoryImpl.kt    # Implementação Auth
│           │   │   │   ├── TaskRepository.kt        # Interface tarefas
│           │   │   │   └── TaskRepositoryImpl.kt    # Implementação Firestore
│           │   │   │
│           │   │   └── Response.kt       # Sealed class para estados
│           │   │
│           │   ├── di/
│           │   │   └── AppModule.kt      # Módulo Hilt (DI)
│           │   │
│           │   ├── ui/
│           │   │   ├── auth/
│           │   │   │   ├── AuthViewModel.kt   # ViewModel autenticação
│           │   │   │   ├── LoginScreen.kt     # Tela de login
│           │   │   │   └── SignUpScreen.kt    # Tela de cadastro
│           │   │   │
│           │   │   ├── task/
│           │   │   │   ├── TaskViewModel.kt   # ViewModel tarefas
│           │   │   │   ├── ListScreen.kt      # Lista de tarefas
│           │   │   │   └── AddTaskDialog.kt   # Dialog adicionar
│           │   │   │
│           │   │   ├── components/
│           │   │   │   ├── CustomButton.kt    # Botão reutilizável
│           │   │   │   ├── CustomTextField.kt # Campo de texto
│           │   │   │   └── TaskItem.kt        # Item da lista
│           │   │   │
│           │   │   ├── navigation/
│           │   │   │   ├── Routes.kt          # Definição de rotas
│           │   │   │   └── AppNavHost.kt      # Navegação
│           │   │   │
│           │   │   └── theme/
│           │   │       ├── Color.kt           # Paleta de cores
│           │   │       ├── Theme.kt           # Tema Material3
│           │   │       └── Type.kt            # Tipografia
│           │   │
│           │   └── util/
│           │       └── Constants.kt           # Constantes
│           │
│           └── res/
│               ├── values/
│               │   ├── colors.xml
│               │   ├── strings.xml
│               │   └── themes.xml
│               │
│               └── mipmap-*/                  # Ícones do app
│
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
│
├── build.gradle                          # Build do projeto
├── settings.gradle                       # Configurações do Gradle
├── gradle.properties                     # Propriedades do Gradle
│
├── .gitignore                            # Arquivos a ignorar no Git
├── README.md                             # Documentação principal
└── documentacao_tecnica.pdf              # Documento técnico LaTeX

```

## Arquivos Principais e suas Responsabilidades

### 📱 App Core
- **MainActivity.kt**: Activity única que hospeda o Compose
- **TodoApplication.kt**: Application class anotada com @HiltAndroidApp

### 💾 Camada de Dados
- **Task.kt**: Modelo de dados da tarefa
- **Response.kt**: Gerenciamento de estados (Loading/Success/Error)
- **Repositories**: Abstração de acesso aos dados do Firebase

### 🎨 Camada de UI
- **ViewModels**: Gerenciam estado e lógica de negócios
- **Screens**: Telas Composable (Login, SignUp, List)
- **Components**: Componentes reutilizáveis
- **Navigation**: Sistema de navegação entre telas
- **Theme**: Tema, cores e tipografia Material Design 3

### 🔧 Configuração
- **AppModule.kt**: Configuração de injeção de dependências
- **google-services.json**: Configuração do Firebase
- **build.gradle**: Dependências e plugins

## Contagem de Arquivos

### Kotlin (.kt): 19 arquivos principais
- Data Layer: 6 arquivos
- DI: 1 arquivo
- UI Layer: 11 arquivos
- Util: 1 arquivo

### Recursos (res/): 
- layouts XML: 0 (100% Compose)
- values: 3 arquivos (colors, strings, themes)
- drawables/mipmaps: ícones do app

### Configuração:
- Gradle: 3 arquivos
- Manifest: 1 arquivo
- Firebase: 1 arquivo

**Total aproximado: ~28 arquivos de código essenciais**

## Para o Repositório Git

### Incluir:
✅ Todos os arquivos .kt
✅ build.gradle e configurações
✅ README.md
✅ Documentação técnica (PDF)
✅ .gitignore
✅ AndroidManifest.xml
✅ Recursos (res/)

### NÃO Incluir (.gitignore):
❌ google-services.json (contém chaves API)
❌ pasta build/
❌ pasta .gradle/
❌ pasta .idea/
❌ arquivos locais (.iml, local.properties)

### .gitignore Recomendado

```gitignore
# Built application files
*.apk
*.ap_
*.aab

# Files for the ART/Dalvik VM
*.dex

# Java class files
*.class

# Generated files
../../../../../../Downloads/bin/
gen/
out/
release/

# Gradle files
.gradle/
build/

# Local configuration file
local.properties

# Android Studio files
.idea/
*.iml
.DS_Store
/captures
.externalNativeBuild
.cxx

# Keystore files
*.jks
*.keystore

# Google Services (Firebase config)
google-services.json

# OS-specific files
.DS_Store
Thumbs.db
```

## Estrutura Recomendada no GitHub

```
VictorM0nteiro/Todo_APP_with_Firebase_auth/
├── app/                          # Código do aplicativo
├── docs/                         # Documentação
│   ├── documentacao_tecnica.pdf
│   └── apresentacao.pdf
├── screenshots/                  # Capturas de tela
│   ├── login.png
│   ├── signup.png
│   └── list.png
├── .gitignore
├── README.md
├── build.gradle
├── settings.gradle
└── LICENSE (opcional)
```

---

**Nota**: Esta estrutura mostra apenas os arquivos essenciais desenvolvidos.
O projeto completo inclui arquivos gerados automaticamente pelo Android Studio
e dependências gerenciadas pelo Gradle.
