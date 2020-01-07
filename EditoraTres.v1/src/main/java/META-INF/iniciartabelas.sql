insert into menu(nome,descricao, icone,  id, nomepai) values ('Editora3','Editora Três','pe-7s-bookmarks',0,null);

insert into menu(nome,descricao, icone,  id, nomepai) values ('cadastro','Cadastro','pe-7s-server',1,0);
insert into menu(nome,descricao, icone,  id, nomepai) values ('vendedor','Vendedor',null,2,1);
insert into menu(nome,descricao, icone,  id, nomepai) values ('equipe','Equipe',null,25,1);
insert into menu(nome,descricao, icone,  id, nomepai) values ('produto','Produto',null,3,1);
insert into menu(nome,descricao, icone,  id, nomepai) values ('brinde','Brinde',null,4,1);
insert into menu(nome,descricao, icone,  id, nomepai) values ('oferta','Oferta',null,5,1);
insert into menu(nome,descricao, icone,  id, nomepai) values ('relatoriocad','Relatórios',0,6,1);
insert into menu(nome,descricao, icone,  id, nomepai) values ('contrato','Contrato',null,7,1);
insert into menu(nome,descricao, icone,  id, nomepai) values ('comissao','Comissão',null,8,1);

insert into menu(nome,descricao, icone,  id, nomepai) values ('relatorios','Relatórios','pe-7s-graph',9,0);
insert into menu(nome,descricao, icone,  id, nomepai) values ('producao','producao',null,10,9);
insert into menu(nome,descricao, icone,  id, nomepai) values ('assinantes','Assinantes',null,11,9);
insert into menu(nome,descricao, icone,  id, nomepai) values ('pagamento','Pagmento',null,12,9);
insert into menu(nome,descricao, icone,  id, nomepai) values ('fechamento','Fechamento',null,13,9);
insert into menu(nome,descricao, icone,  id, nomepai) values ('contratorel','Contrato',null,14,9);

insert into menu(nome,descricao, icone,  id, nomepai) values ('tabela','Tabelas','pe-7s-albums',15,0);
insert into menu(nome,descricao, icone,  id, nomepai) values ('canal','Canal',null,16,15);
insert into menu(nome,descricao, icone,  id, nomepai) values ('subcanal','SubCanal',null,17,15);
insert into menu(nome,descricao, icone,  id, nomepai) values ('origemvenda','Origem Venda',null,18,15);
insert into menu(nome,descricao, icone,  id, nomepai) values ('maquinetapos','Maquineta POS',null,19,15);
insert into menu(nome,descricao, icone,  id, nomepai) values ('tipomov','Tipo Movimento',null,20,15);

insert into menu(nome,descricao, icone,  id, nomepai) values ('configuracao','Configuração','pe-7s-tools',21,0);
insert into menu(nome,descricao, icone,  id, nomepai) values ('parametros','Parâmetros',null,22,21);
insert into menu(nome,descricao, icone,  id, nomepai) values ('usuario','Usuário',null,23,21);
insert into menu(nome,descricao, icone,  id, nomepai) values ('perfil','Perfil de Acesso',null,24,21);



insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (1,'Brinde',null,'/ui/crud/cadastro/brinde.xhtml','Brinde');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (2,'Comissao',null,'/ui/crud/cadastro/comissao.xhtml','Comissao');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (3,'Contrato',null,'/ui/crud/cadastro/contrato.xhtml','Contrato');
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
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (15,'Canal',null,'/ui/crud/tabela/canal.xhtml','Canal');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (16,'SubCanal',null,'/ui/crud/tabela/subcanal.xhtml','Subcanal');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (17,'Maquineta POs',null,'/ui/crud/tabela/maquinetapos.xhtml','MaquinetaPOS');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (18,'Origem de Venda',null,'/ui/crud/tabela/origemvenda.xhtml','OrigemVenda');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (19,'TipoMov',null,'/ui/crud/tabela/tipomov.xhtml','TipoMov');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (20,'Perfil',null,'/ui/crud/config/perfil.xhtml','Usuario');
insert into infra_modulos(idmodulo,  descricao ,  icone  ,  modulo ,  nomeclasse ) values (21,'Equipe',null,'/ui/crud/cadastro/equipe.xhtml','Equipe');

insert into menu_opcoes(id,idmodulo,menu) values (1,1,'brinde');
insert into menu_opcoes(id,idmodulo,menu) values (2,2,'comissao');
insert into menu_opcoes(id,idmodulo,menu) values (3,3,'contrato');
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
insert into menu_opcoes(id,idmodulo,menu) values (15,15,'canal');
insert into menu_opcoes(id,idmodulo,menu) values (16,16,'subcanal');
insert into menu_opcoes(id,idmodulo,menu) values (17,17,'maquinetapos');
insert into menu_opcoes(id,idmodulo,menu) values (18,18,'origemvenda');
insert into menu_opcoes(id,idmodulo,menu) values (19,19,'tipomov');
insert into menu_opcoes(id,idmodulo,menu) values (20,20,'perfil');
insert into menu_opcoes(id,idmodulo,menu) values (21,21,'equipe');

 
