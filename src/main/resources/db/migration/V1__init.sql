create table work
(
    id bigint generated always as identity,
    constraint work_pkey primary key (id)
);

create table expression
(
    id bigint generated always as identity,
    constraint expression_pkey primary key (id)
);

create table internal_codelist
(
    list  integer not null,
    code  integer not null,
    constraint internal_codelist_pkey primary key (list, code)
);

create table onix_codelist
(
    list  integer not null,
    code  integer not null,
    constraint onix_codelist_pkey primary key (list, code)
);

create table manifestation
(
    id bigint generated always as identity,
    constraint manifestation_pkey primary key (id)
);

create table title
(
    id               bigint generated always as identity,
    parent_id        bigint,
    manifestation_id bigint,
    title_type_list  integer not null,
    title_type_code  integer not null,
    title_order      integer not null,
    value            varchar not null,
    constraint title_pkey primary key (id),
    constraint title_manifestation_id_fkey foreign key (manifestation_id) references manifestation (id),
    constraint title_parent_id_fkey foreign key (parent_id) references title (id),
    constraint title_title_type_internal_codelist_list_code_fkey foreign key (title_type_list, title_type_code) references internal_codelist (list, code)
);

create table work_intellectual_level_xref
(
    work_id                 bigint  not null,
    intellectual_level_list integer not null,
    intellectual_level_code integer not null,
    constraint work_intellectual_level_pkey primary key (work_id, intellectual_level_list, intellectual_level_code),
    constraint work_intellectual_level_work_id_fkey foreign key (work_id) references work (id),
    constraint work_intellectual_level_internal_codelist_list_code_fkey foreign key (intellectual_level_list, intellectual_level_code) references internal_codelist (list, code)
);

create table work_literature_type_xref
(
    work_id              bigint  not null,
    literature_type_list integer not null,
    literature_type_code integer not null,
    constraint work_literature_type_pkey primary key (work_id, literature_type_list, literature_type_code),
    constraint work_literature_type_work_id_fkey foreign key (work_id) references work (id),
    constraint work_literature_type_internal_codelist_list_code_fkey foreign key (literature_type_list, literature_type_code) references internal_codelist (list, code)
);

create table expression_product_content_type_xref
(
    expression_id             bigint  not null,
    product_content_type_list integer not null,
    product_content_type_code integer not null,
    constraint expression_product_content_type_pkey primary key (expression_id, product_content_type_list, product_content_type_code),
    constraint expression_product_content_type_expression_id_fkey foreign key (expression_id) references expression (id),
    constraint expression_product_content_type_onix_codelist_list_code_fkey foreign key (product_content_type_list, product_content_type_code) references onix_codelist (list, code)
);

create table manifestation_title_type_xref
(
    manifestation_id bigint  not null,
    title_type_list  integer not null,
    title_type_code  integer not null,
    constraint manifestation_title_type_pkey primary key (manifestation_id, title_type_list, title_type_code),
    constraint manifestation_title_type_manifestation_id_fkey foreign key (manifestation_id) references manifestation (id),
    constraint manifestation_title_type_internal_codelist_list_code_fkey foreign key (title_type_list, title_type_code) references internal_codelist (list, code)
);