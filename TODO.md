# TODO Vendi

Este arquivo lista o que ainda falta implementar ou finalizar no sistema com base no estado atual do monorepo.

## Prioridade alta

- Finalizar o fluxo de checkout.
  Hoje a tela de checkout coleta dados e forma de pagamento, mas nao cria pedido no backend nem confirma a compra.

- Implementar pedidos (`orders`) de ponta a ponta.
  O backend ja tem modelos de dominio para pedidos, mas ainda nao ha controllers, services, repositorios expostos nem telas funcionais de acompanhamento de pedidos no frontend.

- Implementar wishlist real.
  Existe rota `/wishlist` no frontend e modelos `Wishlist`/`WishlistItem` no backend, mas a tela atual usa produtos carregados do catalogo como simulacao e nao persiste dados do usuario.

- Finalizar gerenciamento de enderecos.
  Hoje existe leitura de `/me/addresses`, mas a tela [`vendi_frontend/src/user/profile/views/MyAddresses.vue`](/home/miguel/Programming/Vendi/vendi_frontend/src/user/profile/views/MyAddresses.vue) ainda nao conclui criar, editar, remover e definir endereco ativo. O backend tambem nao expoe endpoints CRUD dedicados para isso.

## Prioridade media

- Permitir atualizar dados da conta.
  A tela [`vendi_frontend/src/user/profile/views/MyAccount.vue`](/home/miguel/Programming/Vendi/vendi_frontend/src/user/profile/views/MyAccount.vue) carrega o usuario autenticado, mas ainda nao salva alteracoes de perfil nem troca de senha.

- Implementar dashboard administrativo.
  A rota `/admin` existe, mas a view atual e basicamente um shell sem indicadores, listas, acoes administrativas ou visao operacional do sistema.

- Criar fluxo de historico de pedidos no frontend.
  O menu ja referencia `/orders`, mas essa rota ainda nao esta registrada no router e nao existe pagina funcional para isso.

- Integrar enderecos ao checkout.
  O checkout ainda nao reutiliza enderecos salvos do usuario nem permite selecionar endereco ativo vindo do backend.

## Prioridade baixa

- Implementar avaliacoes de produtos.
  O backend possui modelo `Rating`, mas ainda nao ha API publica/autenticada para criar e consultar avaliacoes reais. No frontend, a exibicao de rating ainda parece estatica.

- Melhorar recomendacoes e sugestoes da wishlist.
  A secao "So Para Voce" atualmente e derivada do catalogo carregado, sem logica de personalizacao real.

- Expandir os endpoints `me`.
  Hoje eles cobrem leitura de perfil, produtos e enderecos. Ainda faltam operacoes de manutencao do proprio usuario, como atualizacao de cadastro e configuracoes relacionadas.

## Qualidade e produto

- Definir o fluxo oficial de finalizacao de compra.
  Ainda falta decidir e implementar status do pedido, validacoes, reserva de estoque e comportamento apos pagamento.

- Revisar rotas e menus para remover links quebrados ou concluir as paginas pendentes.
  Especialmente itens como `/orders`, que aparece na navegacao mas ainda nao tem tela/rota funcional.

- Atualizar a documentacao quando esses fluxos forem concluídos.
  Quando checkout, pedidos, wishlist e enderecos estiverem completos, refletir isso em `README.md`, `AGENTS.md` e `CLAUDE.md` se o fluxo de trabalho mudar.
