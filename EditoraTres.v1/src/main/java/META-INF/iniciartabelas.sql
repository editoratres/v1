insert into menu(nome,descricao, icone,  id, nomepai) values ('Editora3','Editora Três','pe-7s-bookmarks',0,null);

insert into menu(nome,descricao, icone,  id, nomepai) values ('cadastro','Cadastro','pe-7s-server',1,0);
insert into menu(nome,descricao, icone,  id, nomepai) values ('vendedor','Vendedor',null,2,1);
insert into menu(nome,descricao, icone,  id, nomepai) values ('equipe','Equipe',null,25,1);
insert into menu(nome,descricao, icone,  id, nomepai) values ('produto','Produto',null,3,1);

insert into menu(nome,descricao, icone,  id, nomepai) values ('oferta','Oferta',null,5,1);
insert into menu(nome,descricao, icone,  id, nomepai) values ('relatoriocad','Relatórios',0,6,1);
 
insert into menu(nome,descricao, icone,  id, nomepai) values ('comissao','Comissão',null,8,1);
insert into menu(nome,descricao, icone,  id, nomepai) values ('fornecedor','Fornecedor',null,26,1);
insert into menu(nome,descricao, icone,  id, nomepai) values ('brindes','Brindes',null,4,1);

insert into menu(nome,descricao, icone,  id, nomepai) values ('brinde','Cadastro de brindes',null,30,4);
insert into menu(nome,descricao, icone,  id, nomepai) values ('brindeentrada','Entrada de Brindes',null,27,4);
insert into menu(nome,descricao, icone,  id, nomepai) values ('brindesaida','Saída de Brindes',null,28,4);
insert into menu(nome,descricao, icone,  id, nomepai) values ('brindedevolucao','Devolução de Brindes',null,29,4);

insert into menu(nome,descricao, icone,  id, nomepai) values ('contratos','Contratos',null,31,1)
insert into menu(nome,descricao, icone,  id, nomepai) values ('contratosentrada','Entrada',null,32,31);
insert into menu(nome,descricao, icone,  id, nomepai) values ('contratossaida','Saída',null,33,31);
insert into menu(nome,descricao, icone,  id, nomepai) values ('contratoscancelamento','Cancelamento',null,34,31);
insert into menu(nome,descricao, icone,  id, nomepai) values ('contratosdigitacao','Digitação',null,35,31);
insert into menu(nome,descricao, icone,  id, nomepai) values ('contratosassinantes','Assinantes',null,36,31);

insert into menu(nome,descricao, icone,  id, nomepai) values ('relatorios','Relatórios','pe-7s-graph',9,0);
insert into menu(nome,descricao, icone,  id, nomepai) values ('producao','producao',null,10,9);
insert into menu(nome,descricao, icone,  id, nomepai) values ('assinantes','Assinantes',null,11,9);
insert into menu(nome,descricao, icone,  id, nomepai) values ('pagamento','Pagmento',null,12,9);
insert into menu(nome,descricao, icone,  id, nomepai) values ('fechamento','Fechamento',null,13,9);
insert into menu(nome,descricao, icone,  id, nomepai) values ('contratorel','Contrato',null,14,9);

insert into menu(nome,descricao, icone,  id, nomepai) values ('tabela','Tabelas','pe-7s-albums',15,0);
insert into menu(nome,descricao, icone,  id, nomepai) values ('pdv','Ponto de Venda',null,16,1);
 
insert into menu(nome,descricao, icone,  id, nomepai) values ('origemvenda','Origem Venda',null,18,15);
insert into menu(nome,descricao, icone,  id, nomepai) values ('maquinetapos','Maquineta POS',null,19,15);
insert into menu(nome,descricao, icone,  id, nomepai) values ('tipomov','Tipo Movimento',null,20,15);
insert into menu(nome,descricao, icone,  id, nomepai) values ('bandeiraCartao','Bandeira de cartão',null,37,15);

insert into menu(nome,descricao, icone,  id, nomepai) values ('configuracao','Configuração','pe-7s-tools',21,0);
insert into menu(nome,descricao, icone,  id, nomepai) values ('parametros','Parâmetros',null,22,21);
insert into menu(nome,descricao, icone,  id, nomepai) values ('usuario','Usuário',null,23,21);
insert into menu(nome,descricao, icone,  id, nomepai) values ('perfil','Perfil de Acesso',null,24,21);



insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (1,'Brinde',null,'/ui/crud/cadastro/brinde.xhtml','Brinde');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (2,'Comissao',null,'/ui/crud/cadastro/comissao.xhtml','Comissao');

insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (4,'Oferta',null,'/ui/crud/cadastro/oferta.xhtml','Oferta');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (5,'Produto',null,'/ui/crud/cadastro/produto.xhtml','Produto');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (6,'Relatorioscad',null,'/ui/crud/cadastro/relatorioscad.xhtml','RelatoriosCad');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (7,'Vendedor',null,'/ui/crud/cadastro/vendedor.xhtml','Vendedor');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (8,'Parametros',null,'/ui/crud/config/parametro.xhtml','Parametro');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (9,'Usuários',null,'/ui/crud/config/usuario.xhtml','Usuario');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (10,'Assinantes Rel',null,'/ui/crud/relatorios/assinantes.xhtml','AssinantesRel');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (11,'Contratos Rel',null,'/ui/crud/relatorios/contratos.xhtml','ContratosRel');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (12,'Fechamento Rel',null,'/ui/crud/relatorios/fechamento.xhtml','FechamentoRel');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (13,'Pagamento Rel',null,'/ui/crud/relatorios/pagamentos.xhtml','PagamentosRel');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (14,'Producao Rel',null,'/ui/crud/relatorios/producao.xhtml','ProducaoRel');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (15,'Pdv',null,'/ui/crud/tabela/canal.xhtml','Ponto de venda');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (17,'Maquineta POs',null,'/ui/crud/tabela/maquinetapos.xhtml','MaquinetaPOS');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (18,'Origem de Venda',null,'/ui/crud/tabela/origemvenda.xhtml','OrigemVenda');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (19,'TipoMov',null,'/ui/crud/tabela/tipomov.xhtml','TipoMov');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (20,'Perfil',null,'/ui/crud/config/perfil.xhtml','Usuario');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (21,'Equipe',null,'/ui/crud/cadastro/equipe.xhtml','Equipe');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (22,'Fornecedor',null,'/ui/crud/cadastro/fornecedor.xhtml','Fornecedor');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (23,'BrindeEntrada',null,'/ui/crud/cadastro/brindeentrada.xhtml','Entrada de Brinde');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (24,'BrindeSaida',null,'/ui/crud/cadastro/brindesaida.xhtml','Saída  de Brinde');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (25,'BrindeDev',null,'/ui/crud/cadastro/brindedev.xhtml','Devolução de Brinde');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (26,'ContratoEntrada',null,'/ui/crud/cadastro/contratoentrada.xhtml','Entrada de contratos');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (27,'ContratoSaida',null,'/ui/crud/cadastro/contratosaida.xhtml','Saída de contratos');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (28,'ContratoCancel',null,'/ui/crud/cadastro/contratocancelamento.xhtml','Cancelamento de contratos');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (29,'ContratoDigit',null,'/ui/crud/cadastro/contratodigitacao.xhtml','Digitação de contratos');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (30,'ContratoAssinante',null,'/ui/crud/cadastro/contratoassinante.xhtml','Assinantes dos contratos');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (31,'BandeiraCartao',null,'/ui/crud/tabela/bandeiraCartao.xhtml','Bandeira de Cartão');


insert into menu_opcoes(id,idmodulo,menu) values (1,1,'brinde');
insert into menu_opcoes(id,idmodulo,menu) values (2,2,'comissao');
insert into menu_opcoes(id,idmodulo,menu) values (4,4,'oferta');	
insert into menu_opcoes(id,idmodulo,menu) values (5,5,'produto');	
insert into menu_opcoes(id,idmodulo,menu) values (6,6,'relatoriocad');	
insert into menu_opcoes(id,idmodulo,menu) values (7,7,'vendedor');	
insert into menu_opcoes(id,idmodulo,menu) values (8,8,'parametros');	
insert into menu_opcoes(id,idmodulo,menu) values (9,9,'usuario');
insert into menu_opcoes(id,idmodulo,menu) values (10,10,'assinantes');
insert into menu_opcoes(id,idmodulo,menu) values (11,11,'contratorel');
insert into menu_opcoes(id,idmodulo,menu) values (12,12,'fechamento');
insert into menu_opcoes(id,idmodulo,menu) values (13,13,'pagamento');
insert into menu_opcoes(id,idmodulo,menu) values (14,14,'producao');
insert into menu_opcoes(id,idmodulo,menu) values (15,15,'pdv');
insert into menu_opcoes(id,idmodulo,menu) values (17,17,'maquinetapos');
insert into menu_opcoes(id,idmodulo,menu) values (18,18,'origemvenda');
insert into menu_opcoes(id,idmodulo,menu) values (19,19,'tipomov');
insert into menu_opcoes(id,idmodulo,menu) values (20,20,'perfil');
insert into menu_opcoes(id,idmodulo,menu) values (21,21,'equipe');
insert into menu_opcoes(id,idmodulo,menu) values (22,22,'fornecedor');
insert into menu_opcoes(id,idmodulo,menu) values (23,23,'brindeentrada');
insert into menu_opcoes(id,idmodulo,menu) values (24,24,'brindesaida');
insert into menu_opcoes(id,idmodulo,menu) values (25,25,'brindedevolucao');
insert into menu_opcoes(id,idmodulo,menu) values (26,26,'contratosentrada');
insert into menu_opcoes(id,idmodulo,menu) values (27,27,'contratossaida');
insert into menu_opcoes(id,idmodulo,menu) values (28,28,'contratoscancelamento');
insert into menu_opcoes(id,idmodulo,menu) values (29,29,'contratosdigitacao');
insert into menu_opcoes(id,idmodulo,menu) values (30,30,'contratosassinantes');
 insert into menu_opcoes(id,idmodulo,menu) values (31,31,'bandeiraCartao');
