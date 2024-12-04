Sistema de Gestão de Visualizar filmes
Este é um projeto de sistema de gestão de locação de veículos, desenvolvido em Java com Spring Boot. Ele permite a administração de uma frota de veículos, autenticação JWT, gestão de reservas, e cálculo de tarifas, entre outras funcionalidades.

Funcionalidades
Autenticação e Autorização: Sistema de login com autenticação JWT e controle de acesso para diferentes papéis de usuário (Admin e Cliente).
Gestão de Frota: Cadastro, atualização, e exclusão de veículos, incluindo controle de disponibilidade e manutenção.
Reservas de Veículos: Clientes podem fazer reservas de veículos com cálculo automático de tarifas com base no tempo de locação.
Cálculo de Tarifas: Tarifas de locação são calculadas automaticamente considerando o período da reserva.
Controle de Acesso: Administração do sistema é restrita a usuários com o papel de Admin, enquanto Clientes têm permissões limitadas.
Tecnologias Utilizadas
Java 21
Spring Boot 3.3.4
Spring Security: Implementação de autenticação JWT
Maven: Gerenciamento de dependências e build
Banco de Dados: PostgreSql
Estrutura do Projeto
Controller: Contém controladores para gerenciar a lógica dos diferentes módulos, como:
AuthController: Gerencia a autenticação de usuários.
AdminController: Controla operações específicas para administradores.
ClienteController: Gerencia operações de clientes.
DTO (Data Transfer Objects): Classes que facilitam a transferência de dados entre o cliente e o servidor.
Services: Implementação da lógica de negócios para cada funcionalidade.
Repository: Interface para interagir com o banco de dados.
Endpoints Principais
Autenticação (/auth)

POST /auth/login: Realiza a autenticação do usuário e retorna um token JWT.
Gestão de Usuários (api/admin/usuario)

GET /admin/usuario: Lista todos os usuários (restrito a Admins).
PUT /admin/usuario/{id}: Atualiza informações de um usuário específico (restrito a Admins).
Gestão de Veículos (api/admin/veiculo)

POST /veiculo: Adiciona um novo veículo (restrito a Admins).
PUT /veiculo/{id}: Atualiza os dados de um veículo (restrito a Admins).
DELETE /veiculo/{id}: Remove um veículo do sistema (restrito a Admins).
Reservas (api/cliente/reserva)

POST /reserva: Cria uma nova reserva de veículo.
GET /reserva: Lista as reservas feitas pelo cliente autenticado.
