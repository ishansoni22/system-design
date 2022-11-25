CREATE TABLE public.parking_lot (
    id varchar(255) NOT NULL,
    name varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE public.spot (
    id varchar(255) NOT NULL,
    configurations json,
    parking_lot_id varchar(255),
    type varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE public.terminal (
    id varchar(255) NOT NULL,
    parking_lot_id varchar(255),
    picker_strategy varchar(255),
    type varchar(255),
    PRIMARY KEY (id)
);

INSERT INTO public.parking_lot (id, name) VALUES
('P001', 'Test Parking Lot');

INSERT INTO public.spot (id, configurations, parking_lot_id, type) VALUES
('0', '{
	"ET001.distance": "0",
	"ET002.distance": "9"
}', 'P001', NULL),
('1', '{
	"ET001.distance": "1",
	"ET002.distance": "8"
}', 'P001', NULL),
('2', '{
	"ET001.distance": "2",
	"ET002.distance": "7"
}', 'P001', NULL),
('3', '{
	"ET001.distance": "3",
	"ET002.distance": "6"
}', 'P001', NULL),
('4', '{
	"ET001.distance": "4",
	"ET002.distance": "5"
}', 'P001', NULL),
('5', '{
	"ET001.distance": "5",
	"ET002.distance": "4"
}', 'P001', NULL),
('6', '{
	"ET001.distance": "6",
	"ET002.distance": "3"
}', 'P001', NULL),
('7', '{
	"ET001.distance": "7",
	"ET002.distance": "2"
}', 'P001', NULL),
('8', '{
	"ET001.distance": "8",
	"ET002.distance": "1"
}', 'P001', NULL),
('9', '{
	"ET001.distance": "9",
	"ET002.distance": "0"
}', 'P001', NULL);

INSERT INTO public.terminal (id, parking_lot_id, picker_strategy, type) VALUES
('ET001', 'P001', 'NEAREST', 'ENTRY'),
('ET002', 'P001', 'NEAREST', 'ENTRY');