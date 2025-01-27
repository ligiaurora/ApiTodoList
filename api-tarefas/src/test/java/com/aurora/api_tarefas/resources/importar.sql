DELETE FROM todos;

INSERT INTO todos (id, nome, descricao, realizado, prioridade, prazo, categoria, data_criacao, data_conclusao) 
VALUES (1, 'Tarefa Teste I', 'Descrição da tarefa teste I', false, true, DATE_ADD(NOW(), INTERVAL 1 DAY), 'OUTROS', NOW(), NULL);

INSERT INTO todos (id, nome, descricao, realizado, prioridade, prazo, categoria, data_criacao, data_conclusao) 
VALUES (2, 'Tarefa Teste II', 'Descrição da tarefa teste II', true, false, DATE_ADD(NOW(), INTERVAL 5 DAY), 'LAZER', NOW(), NOW());

INSERT INTO todos (id, nome, descricao, realizado, prioridade, prazo, categoria, data_criacao, data_conclusao) 
VALUES (3, 'Tarefa Teste III', 'Descrição da tarefa teste III', true, false, DATE_ADD(NOW(), INTERVAL 5 DAY), 'TRABALHO', NOW(), NOW());






