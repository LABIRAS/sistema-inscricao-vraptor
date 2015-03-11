    alter table Inscrito 
        drop 
        foreign key FK_64s1yncvdn6j3j6r29l6hc7w0

    drop table if exists Configuration

    drop table if exists Etapa

    drop table if exists Inscrito

    drop table if exists User

    create table Configuration (
        id bigint not null auto_increment,
        config varchar(128) not null,
        value varchar(2048),
        primary key (id)
    ) ENGINE=InnoDB

    create table Etapa (
        id bigint not null auto_increment,
        dataFim datetime,
        dataInicio datetime not null,
        inscritos integer not null,
        nome varchar(256) not null,
        preco integer not null,
        primary key (id)
    ) ENGINE=InnoDB

    create table Inscrito (
        id bigint not null auto_increment,
        cidade varchar(128) not null,
        comoFicouSabendoDoEvento varchar(64) not null,
        cpf varchar(32) not null,
        curso varchar(256),
        dataInscricao datetime not null,
        dataPagamento datetime,
        email varchar(256) not null,
        empresa varchar(256),
        estudante bit not null,
        idade integer not null,
        jaConheceArduino bit not null,
        jaUsouArduino bit not null,
        nivelGraduacao integer,
        nome varchar(256) not null,
        pago bit not null,
        profissional bit not null,
        uf varchar(6) not null,
        ultimaInstituicao varchar(256),
        etapa_id bigint not null,
        primary key (id)
    ) ENGINE=InnoDB

    create table User (
        id bigint not null auto_increment,
        dateCreated datetime not null,
        email varchar(128) not null,
        lastLogin datetime,
        lastUpdated datetime,
        password varchar(128) not null,
        role varchar(64) not null,
        username varchar(128) not null,
        primary key (id)
    ) ENGINE=InnoDB

    alter table Configuration 
        add constraint UK_ax94ekxdekx88c0uc61q3lswc  unique (config)

    alter table Etapa 
        add constraint UK_792g877oic0djtfl77pfu77jc  unique (nome)

    alter table Inscrito 
        add constraint UK_tf8cait0xhx077w6o7yo5a1t6  unique (cpf)

    alter table Inscrito 
        add constraint UK_m3jkjfj8ayjd9s8s72tvnvpgn  unique (email)

    alter table User 
        add constraint UK_e6gkqunxajvyxl5uctpl2vl2p  unique (email)

    alter table User 
        add constraint UK_jreodf78a7pl5qidfh43axdfb  unique (username)

    alter table Inscrito 
        add constraint FK_64s1yncvdn6j3j6r29l6hc7w0 
        foreign key (etapa_id) 
        references Etapa (id)