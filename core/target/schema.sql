
    alter table Activity 
        drop 
        foreign key FK_4n33agrv3lvot575olldcvqca;

    alter table Goal 
        drop 
        foreign key FK_6qbhrnxafk8lemxj1tnh5tqvd;

    alter table goal_activity 
        drop 
        foreign key FK_4jrodd3f99vv2w6frkjopemvd;

    alter table goal_activity 
        drop 
        foreign key FK_giwxxpijbeh7hyrsbqgevps7t;

    alter table user_role 
        drop 
        foreign key FK_it77eq964jhfqtu54081ebtio;

    alter table user_role 
        drop 
        foreign key FK_apcc8lxk2xnug8377fatvbn04;

    drop table if exists Activity;

    drop table if exists DailyRecord;

    drop table if exists Goal;

    drop table if exists Plan;

    drop table if exists app_user;

    drop table if exists goal_activity;

    drop table if exists role;

    drop table if exists user_role;

    create table Activity (
        ID bigint not null auto_increment,
        actType integer,
        name varchar(255),
        unit integer,
        value double precision not null,
        dailyRecord_ID bigint,
        primary key (ID)
    ) ENGINE=InnoDB;

    create table DailyRecord (
        ID bigint not null auto_increment,
        date date,
        memberID bigint,
        primary key (ID)
    ) ENGINE=InnoDB;

    create table Goal (
        ID bigint not null auto_increment,
        actType integer,
        completed bit not null,
        name varchar(255),
        progress double precision not null,
        unit integer,
        value double precision not null,
        plan_ID bigint,
        primary key (ID)
    ) ENGINE=InnoDB;

    create table Plan (
        ID bigint not null auto_increment,
        fromDate date,
        memberID bigint not null,
        name varchar(255),
        primary key (ID)
    ) ENGINE=InnoDB;

    create table app_user (
        id bigint not null auto_increment,
        account_expired bit not null,
        account_locked bit not null,
        address varchar(150),
        city varchar(50),
        country varchar(100),
        postal_code varchar(15),
        province varchar(100),
        credentials_expired bit not null,
        email varchar(255) not null,
        account_enabled bit,
        first_name varchar(50) not null,
        last_name varchar(50) not null,
        password varchar(255) not null,
        password_hint varchar(255),
        phone_number varchar(255),
        username varchar(50) not null,
        version integer,
        website varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table goal_activity (
        goalID bigint not null,
        activityID bigint not null,
        primary key (goalID, activityID)
    ) ENGINE=InnoDB;

    create table role (
        id bigint not null auto_increment,
        description varchar(64),
        name varchar(20),
        primary key (id)
    ) ENGINE=InnoDB;

    create table user_role (
        user_id bigint not null,
        role_id bigint not null,
        primary key (user_id, role_id)
    ) ENGINE=InnoDB;

    alter table app_user 
        add constraint UK_1j9d9a06i600gd43uu3km82jw  unique (email);

    alter table app_user 
        add constraint UK_3k4cplvh82srueuttfkwnylq0  unique (username);

    alter table Activity 
        add constraint FK_4n33agrv3lvot575olldcvqca 
        foreign key (dailyRecord_ID) 
        references DailyRecord (ID);

    alter table Goal 
        add constraint FK_6qbhrnxafk8lemxj1tnh5tqvd 
        foreign key (plan_ID) 
        references Plan (ID);

    alter table goal_activity 
        add constraint FK_4jrodd3f99vv2w6frkjopemvd 
        foreign key (activityID) 
        references Activity (ID);

    alter table goal_activity 
        add constraint FK_giwxxpijbeh7hyrsbqgevps7t 
        foreign key (goalID) 
        references Goal (ID);

    alter table user_role 
        add constraint FK_it77eq964jhfqtu54081ebtio 
        foreign key (role_id) 
        references role (id);

    alter table user_role 
        add constraint FK_apcc8lxk2xnug8377fatvbn04 
        foreign key (user_id) 
        references app_user (id);
