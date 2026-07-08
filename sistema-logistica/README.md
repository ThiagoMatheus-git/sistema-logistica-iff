# Sistema Web para Controle de Entregas e Logística

## Informações do Trabalho
- **Disciplina:** Desenvolvimento de Aplicações
- **Curso:** Sistemas de Informação – 5º Período
- **Tecnologias:** Eclipse JEE (2020-06), WildFly (20.0.1), JSF 2.3, JPA, MySQL

---

## 📁 Estrutura do Projeto

```
sistema-logistica/
├── model/
│   ├── Endereco.java          # Entidade Endereco
│   ├── Pessoa.java            # Classe abstrata Pessoa (@MappedSuperclass)
│   ├── Cliente.java           # Entidade Cliente (herda Pessoa)
│   ├── Entregador.java        # Entidade Entregador (herda Pessoa)
│   └── Encomenda.java         # Entidade Encomenda
├── dao/
│   ├── DAOGenerico.java       # DAO genérico com operações CRUD
│   ├── ClienteDAO.java        # DAO de Cliente com JPA Criteria
│   ├── EntregadorDAO.java     # DAO de Entregador com JPA Criteria
│   └── EncomendaDAO.java      # DAO de Encomenda com JPA Criteria
├── controller/
│   ├── ClienteController.java     # Managed Bean JSF - Clientes
│   ├── EntregadorController.java  # Managed Bean JSF - Entregadores
│   ├── EncomendaController.java   # Managed Bean JSF - Encomendas
│   └── RelatorioController.java   # Managed Bean JSF - Relatórios
├── converter/
│   └── CPFConverter.java      # Conversor para formatar CPF (xxx.xxx.xxx-xx)
├── util/
│   └── Util.java              # Utilitários (mensagens, tratamento de erros)
├── templates/
│   └── template.xhtml         # Template principal com menu e rodapé
├── index.xhtml                # Página inicial com links de navegação
├── privado/
│   ├── cliente/listar.xhtml   # Tela Gerenciar Clientes
│   ├── entregador/listar.xhtml # Tela Gerenciar Entregadores
│   ├── encomenda/listar.xhtml  # Tela Registrar Encomendas
│   └── relatorio/listar.xhtml  # Tela Relatórios de Entregas
├── resources/css/
│   └── estilos.css            # Estilos CSS personalizados
├── WEB-INF/
│   ├── web.xml                # Configuração do Servlet Faces
│   ├── faces-config.xml       # Configuração JSF e navegação
│   └── beans.xml              # Ativação CDI
└── META-INF/
    └── persistence.xml        # Configuração JPA/Hibernate
```

---

## 🗺️ Diagrama de Classes (Conforme Trabalho)

```
┌──────────┐        ┌──────────┐        ┌──────────┐
│ Cliente  │◄───────│  Pessoa  │────────│ Endereco │
│ - cpf    │        │ - nome   │   1..1 │ - rua    │
└────┬─────┘        │ - celular│        │ - numero │
     │ 1            └────┬─────┘        │ - bairro │
     │                   ▲              │ - cidade │
     │                   │              └──────────┘
     │ 0..*         ┌────┴─────┐
     └─────────────►│Entregador│
                    │ - cnh    │
     0..*           │ - categoriaCnh │
     └──────────────►└────┬─────┘
     │                    │ 1
     │                    │
     │              ┌─────┴─────┐
     └─────────────►│ Encomenda │
                    │ - codigoRastreio │
                    │ - valor          │
                    └──────────────────┘
```

---

## ⚙️ Configuração do Banco de Dados (MySQL)

### 1. Criar o banco de dados:
```sql
CREATE DATABASE sistemalogistica CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Criar usuário (opcional):
```sql
CREATE USER 'logistica'@'localhost' IDENTIFIED BY 'logistica123';
GRANT ALL PRIVILEGES ON sistemalogistica.* TO 'logistica'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configurar DataSource no WildFly:
No arquivo `standalone.xml`, adicione:
```xml
<datasource jndi-name="java:/SistemaLogisticaDS" pool-name="SistemaLogisticaPool">
    <connection-url>jdbc:mysql://localhost:3306/sistemalogistica</connection-url>
    <driver>mysql</driver>
    <security>
        <user-name>logistica</user-name>
        <password>logistica123</password>
    </security>
</datasource>
```

---

## 📋 Requisitos Atendidos

### 1. Modelagem e Mapeamento (0,8)
- ✅ Classes modeladas conforme diagrama UML
- ✅ Herança com `@MappedSuperclass` (Pessoa)
- ✅ Relacionamentos 1:1 (Pessoa-Endereco), 1:N (Pessoa-Encomenda), N:1 (Encomenda-Cliente/Entregador)
- ✅ Multiplicidades respeitadas

### 2. Tela Inicial (0,2)
- ✅ Cabeçalho com título oficial
- ✅ Links para as 4 telas do sistema
- ✅ Rodapé com identificação

### 3. Tela Gerenciar Entregadores (1,0)
- ✅ Cadastrar, editar, excluir, listar, filtrar
- ✅ `<h:selectOneMenu>` para categoria CNH (A, B, AB)
- ✅ Todos os dados obrigatórios (pessoais, endereço, profissionais)
- ✅ Listagem ordenada crescente por nome
- ✅ Regra de bloqueio: não excluir entregador com encomendas
- ✅ Filtro por nome (LIKE)

### 4. Tela Gerenciar Clientes (1,0)
- ✅ Cadastrar dados pessoais e endereço completo
- ✅ Não permitir CPF duplicado
- ✅ CPF não editável na edição
- ✅ Listagem ordenada por nome
- ✅ Todos os campos obrigatórios

### 5. Tela Registrar Encomendas (1,3)
- ✅ Código de rastreio e valor
- ✅ Seleção obrigatória de Cliente e Entregador
- ✅ Endereço de entrega na listagem
- ✅ Todos os campos obrigatórios

### 6. Tela Relatórios de Entregas (2,0)
- ✅ Filtro por entregador (específico ou "Todos")
- ✅ Filtro por cidade do cliente (LIKE)
- ✅ Filtro por valor máximo (menor que)
- ✅ Filtros combinados ou individuais
- ✅ Campos em branco/"Todos" são ignorados
- ✅ Ordenação por nome do cliente

### 7. Validações e Navegação (0,2)
- ✅ CPF formatado (xxx.xxx.xxx-xx) via Converter
- ✅ Link para página inicial em todas as telas (menu)
- ✅ Telas resetadas após ações (salvar, excluir, filtrar)

---

## 🔧 Tecnologias Utilizadas

| Tecnologia | Versão |
|------------|--------|
| Eclipse JEE | 2020-06 |
| WildFly | 20.0.1 |
| JSF | 2.3 |
| JPA (Hibernate) | 5.x |
| PrimeFaces | 8.0+ |
| MySQL | 8.0+ |
| Java EE / Jakarta EE | 8 |

---

## 🚀 Como Executar

1. Importe o projeto no Eclipse JEE como projeto Maven/Dynamic Web
2. Configure o Server Runtime para WildFly 20.0.1
3. Configure o DataSource no WildFly conforme instruções acima
4. Deploy do projeto no servidor
5. Acesse: `http://localhost:8080/sistema-logistica/`

---

## 📌 Observações Importantes

- Todas as consultas e filtros utilizam **JPA Criteria API**
- O `hibernate.hbm2ddl.auto` está como `update` para criar as tabelas automaticamente
- Para produção, altere para `validate` ou remova
- Plágio resultará em nota 0 para todos os envolvidos
