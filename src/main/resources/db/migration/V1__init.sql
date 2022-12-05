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

create table codelist
(
    list  integer not null,
    code  integer not null,
    dtype varchar,
    constraint codelist_pkey primary key (list, code)
);

create table work_intellectual_level_xref
(
    work_id                 bigint  not null,
    intellectual_level_list integer not null,
    intellectual_level_code integer not null,
    constraint work_intellectual_level_pkey primary key (work_id, intellectual_level_list, intellectual_level_code),
    constraint work_intellectual_level_work_id_fkey foreign key (work_id) references work (id),
    constraint work_intellectual_level_codelist_list_code_fkey foreign key (intellectual_level_list, intellectual_level_code) references public.codelist (list, code)
);

create table work_literature_type_xref
(
    work_id              bigint  not null,
    literature_type_list integer not null,
    literature_type_code integer not null,
    constraint work_literature_type_pkey primary key (work_id, literature_type_list, literature_type_code),
    constraint work_literature_type_work_id_fkey foreign key (work_id) references work (id),
    constraint work_literature_type_codelist_list_code_fkey foreign key (literature_type_list, literature_type_code) references public.codelist (list, code)
);

create table expression_literature_type_xref
(
    expression_id        bigint  not null,
    literature_type_list integer not null,
    literature_type_code integer not null,
    constraint expression_literature_type_pkey primary key (expression_id, literature_type_list, literature_type_code),
    constraint expression_literature_type_work_id_fkey foreign key (expression_id) references expression (id),
    constraint expression_literature_type_codelist_list_code_fkey foreign key (literature_type_list, literature_type_code) references public.codelist (list, code)
);