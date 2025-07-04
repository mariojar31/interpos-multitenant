-- Table: barcode_type
CREATE TABLE IF NOT EXISTS `barcode_type` (
                                              `id_barcode_type` smallint NOT NULL,
                                              `created_at` datetime(6) DEFAULT NULL,
    `name` varchar(255) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id_barcode_type`),
    UNIQUE KEY `UKrq5dvphgtnrewiqjej7idpcql` (`name`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: country
CREATE TABLE IF NOT EXISTS `country` (
                                         `id_country` smallint NOT NULL,
                                         `code` varchar(3) NOT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `name` varchar(255) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id_country`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: department
CREATE TABLE IF NOT EXISTS `department` (
                                            `id_department` smallint NOT NULL,
                                            `code` varchar(2) DEFAULT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `name` varchar(255) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `country_id` smallint NOT NULL,
    PRIMARY KEY (`id_department`),
    KEY `FKk0qqx5jjcwjwd8wut9t7rl23` (`country_id`),
    CONSTRAINT `FKk0qqx5jjcwjwd8wut9t7rl23` FOREIGN KEY (`country_id`) REFERENCES `country` (`id_country`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: municipality
CREATE TABLE IF NOT EXISTS `municipality` (
                                              `id_municipality` bigint NOT NULL,
                                              `code` varchar(5) DEFAULT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `name` varchar(255) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `department_id` smallint NOT NULL,
    PRIMARY KEY (`id_municipality`),
    KEY `FKnqlbbs8pohavqnbhg5oo1d7th` (`department_id`),
    CONSTRAINT `FKnqlbbs8pohavqnbhg5oo1d7th` FOREIGN KEY (`department_id`) REFERENCES `department` (`id_department`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: identification_type
CREATE TABLE IF NOT EXISTS `identification_type` (
                                                     `id_identification_type` smallint NOT NULL,
                                                     `abbreviation` varchar(10) NOT NULL,
    `code` varchar(2) NOT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `name` varchar(255) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id_identification_type`),
    UNIQUE KEY `UKifmydsrfmuc4vuor2xf7lgij5` (`code`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: type_organization
CREATE TABLE IF NOT EXISTS `type_organization` (
                                                   `id_type_organization` smallint NOT NULL,
                                                   `code` varchar(2) NOT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `name` varchar(255) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id_type_organization`),
    UNIQUE KEY `UK7kc2nhljouqg467l2o6jx06f4` (`code`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: type_regime
CREATE TABLE IF NOT EXISTS `type_regime` (
                                             `id_type_regime` smallint NOT NULL,
                                             `code` varchar(2) NOT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `name` varchar(255) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id_type_regime`),
    UNIQUE KEY `UKgsafl689f5aba4ekaqr268mie` (`code`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: type_responsibility
CREATE TABLE IF NOT EXISTS `type_responsibility` (
                                                     `id_type_responsibility` smallint NOT NULL,
                                                     `code` varchar(7) NOT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `name` varchar(255) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `api_responsibility_id` smallint NOT NULL,
    PRIMARY KEY (`id_type_responsibility`),
    UNIQUE KEY `UKaqb53xvxo20vvhc3nt1q4tcm0` (`code`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: company
CREATE TABLE IF NOT EXISTS `company` (
                                         `id_company` bigint NOT NULL AUTO_INCREMENT,
                                         `address` varchar(100) DEFAULT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `identification_number` varchar(15) DEFAULT NULL,
    `image` mediumblob,
    `mail` varchar(100) DEFAULT NULL,
    `name` varchar(100) DEFAULT NULL,
    `phone` varchar(50) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `visible` tinyint(1) DEFAULT '1',
    `identification_type_id` smallint NOT NULL,
    `municipality_id` bigint DEFAULT NULL,
    `type_organization_id` smallint DEFAULT NULL,
    `type_regime_id` smallint DEFAULT NULL,
    `type_responsibility_id` smallint DEFAULT NULL,
    `certificate` mediumblob,
    `password_certificate` varchar(15) DEFAULT NULL,
    `tax_id` smallint DEFAULT NULL,
    PRIMARY KEY (`id_company`),
    KEY `FKkanjlxhr9j616c51y30jxpen7` (`identification_type_id`),
    KEY `FKj62tjrmbpru7erwi08ycdob3w` (`municipality_id`),
    KEY `FK8hayyf5t50khsu0gq7wus75rh` (`type_organization_id`),
    KEY `FKlrpht2hpo4ypgs4mbfkm1tb9e` (`type_regime_id`),
    KEY `FKlhyo1s26wgj40j7snhu1qgbbj` (`type_responsibility_id`),
    CONSTRAINT `FK8hayyf5t50khsu0gq7wus75rh` FOREIGN KEY (`type_organization_id`) REFERENCES `type_organization` (`id_type_organization`),
    CONSTRAINT `FKj62tjrmbpru7erwi08ycdob3w` FOREIGN KEY (`municipality_id`) REFERENCES `municipality` (`id_municipality`),
    CONSTRAINT `FKkanjlxhr9j616c51y30jxpen7` FOREIGN KEY (`identification_type_id`) REFERENCES `identification_type` (`id_identification_type`),
    CONSTRAINT `FKlhyo1s26wgj40j7snhu1qgbbj` FOREIGN KEY (`type_responsibility_id`) REFERENCES `type_responsibility` (`id_type_responsibility`),
    CONSTRAINT `FKlrpht2hpo4ypgs4mbfkm1tb9e` FOREIGN KEY (`type_regime_id`) REFERENCES `type_regime` (`id_type_regime`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: branch
CREATE TABLE IF NOT EXISTS `branch` (
                                        `id_branch` bigint NOT NULL AUTO_INCREMENT,
                                        `address` varchar(100) DEFAULT NULL,
    `branch_code` varchar(10) DEFAULT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `image` mediumblob,
    `mail` varchar(100) DEFAULT NULL,
    `name` varchar(100) DEFAULT NULL,
    `phone` varchar(50) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `visible` tinyint(1) DEFAULT '1',
    `company_id` bigint NOT NULL,
    `municipality_id` bigint DEFAULT NULL,
    `parent_id` bigint DEFAULT NULL,
    `type_organization_id` smallint DEFAULT NULL,
    `type_regime_id` smallint DEFAULT NULL,
    `type_responsibility_id` smallint DEFAULT NULL,
    PRIMARY KEY (`id_branch`),
    KEY `FK14f9k065wqeubl6tl0gdumcp5` (`company_id`),
    KEY `FKb9atliqsm0bd7j2ktmnx0qb3f` (`municipality_id`),
    KEY `FKqi6b3wpl77vybu6nwkh4jylib` (`parent_id`),
    KEY `FKeypgyh113v4eon96g0mqwyoul` (`type_organization_id`),
    KEY `FKsvu1maombc7jc9shify38xm73` (`type_regime_id`),
    KEY `FKmkrfjpk96otukllhi0dko7ofc` (`type_responsibility_id`),
    CONSTRAINT `FK14f9k065wqeubl6tl0gdumcp5` FOREIGN KEY (`company_id`) REFERENCES `company` (`id_company`),
    CONSTRAINT `FKb9atliqsm0bd7j2ktmnx0qb3f` FOREIGN KEY (`municipality_id`) REFERENCES `municipality` (`id_municipality`),
    CONSTRAINT `FKeypgyh113v4eon96g0mqwyoul` FOREIGN KEY (`type_organization_id`) REFERENCES `type_organization` (`id_type_organization`),
    CONSTRAINT `FKmkrfjpk96otukllhi0dko7ofc` FOREIGN KEY (`type_responsibility_id`) REFERENCES `type_responsibility` (`id_type_responsibility`),
    CONSTRAINT `FKqi6b3wpl77vybu6nwkh4jylib` FOREIGN KEY (`parent_id`) REFERENCES `branch` (`id_branch`),
    CONSTRAINT `FKsvu1maombc7jc9shify38xm73` FOREIGN KEY (`type_regime_id`) REFERENCES `type_regime` (`id_type_regime`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: branch_configuration
CREATE TABLE IF NOT EXISTS `branch_configuration` (
                                                      `id_branch_configuration` bigint NOT NULL AUTO_INCREMENT,
                                                      `code_override` varchar(255) DEFAULT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `footer_invoice` varchar(1000) DEFAULT NULL,
    `header_invoice` varchar(1000) DEFAULT NULL,
    `prefix_invoice` varchar(4) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `value_service_change` int DEFAULT NULL,
    `branch_id` bigint NOT NULL,
    PRIMARY KEY (`id_branch_configuration`),
    UNIQUE KEY `UK1a4ywvhvboon2907mtw490rlr` (`branch_id`),
    CONSTRAINT `FKj1ojnvhmc3wp9g7rbk2xr661u` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id_branch`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: floor
CREATE TABLE IF NOT EXISTS `floor` (
                                       `id_floor` bigint NOT NULL AUTO_INCREMENT,
                                       `created_at` datetime(6) NOT NULL,
    `image` mediumblob,
    `name` varchar(100) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `visible` tinyint(1) DEFAULT '1',
    `branch_id` bigint NOT NULL,
    PRIMARY KEY (`id_floor`),
    KEY `FK56m5txhvx1deamstxwer00k8s` (`branch_id`),
    CONSTRAINT `FK56m5txhvx1deamstxwer00k8s` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id_branch`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: terminal
CREATE TABLE IF NOT EXISTS `terminal` (
                                          `id_terminal` bigint NOT NULL AUTO_INCREMENT,
                                          `code` varchar(50) DEFAULT NULL,
    `created_at` datetime(6) NOT NULL,
    `name` varchar(100) DEFAULT NULL,
    `sequence` int NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `visible` tinyint(1) DEFAULT '1',
    `floor_id` bigint NOT NULL,
    PRIMARY KEY (`id_terminal`),
    KEY `FKpm9e99v7rgxspkvqdbsslqx5q` (`floor_id`),
    CONSTRAINT `FKpm9e99v7rgxspkvqdbsslqx5q` FOREIGN KEY (`floor_id`) REFERENCES `floor` (`id_floor`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: cash
CREATE TABLE IF NOT EXISTS `cash` (
                                      `id_cash` bigint NOT NULL AUTO_INCREMENT,
                                      `created_at` datetime(6) DEFAULT NULL,
    `end_date` datetime(6) DEFAULT NULL,
    `is_closed` bit(1) NOT NULL,
    `sequence` int NOT NULL,
    `start_date` datetime(6) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `terminal_id` bigint NOT NULL,
    PRIMARY KEY (`id_cash`),
    KEY `FKmwr07f36gfrp8pvf615i517ya` (`terminal_id`),
    CONSTRAINT `FKmwr07f36gfrp8pvf615i517ya` FOREIGN KEY (`terminal_id`) REFERENCES `terminal` (`id_terminal`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: tabless
CREATE TABLE IF NOT EXISTS `tabless` (
                                         `id_table` bigint NOT NULL AUTO_INCREMENT,
                                         `code` varchar(50) DEFAULT NULL,
    `created_at` datetime(6) NOT NULL,
    `height` int DEFAULT NULL,
    `name` varchar(100) NOT NULL,
    `position_x` int NOT NULL,
    `position_y` int NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `visible` tinyint(1) DEFAULT '1',
    `width` int DEFAULT NULL,
    `floor_id` bigint NOT NULL,
    PRIMARY KEY (`id_table`),
    KEY `FK46wq930r0bbgjlkb0uoouir43` (`floor_id`),
    CONSTRAINT `FK46wq930r0bbgjlkb0uoouir43` FOREIGN KEY (`floor_id`) REFERENCES `floor` (`id_floor`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: category
CREATE TABLE IF NOT EXISTS `category` (
                                          `id_category` bigint NOT NULL AUTO_INCREMENT,
                                          `code` varchar(50) DEFAULT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `image` mediumblob,
    `name` varchar(100) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `visible` tinyint(1) DEFAULT '1',
    `branch_id` bigint NOT NULL,
    `parent_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id_category`),
    UNIQUE KEY `UK46ccwnsi9409t36lurvtyljak` (`name`),
    KEY `FKylaaeiwtg5lmt938b4v67bgm` (`branch_id`),
    KEY `FK2y94svpmqttx80mshyny85wqr` (`parent_id`),
    CONSTRAINT `FK2y94svpmqttx80mshyny85wqr` FOREIGN KEY (`parent_id`) REFERENCES `category` (`id_category`),
    CONSTRAINT `FKylaaeiwtg5lmt938b4v67bgm` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id_branch`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: type_unit
CREATE TABLE IF NOT EXISTS `type_unit` (
                                           `id_type_unit` smallint NOT NULL,
                                           `abbreviation` varchar(10) NOT NULL,
    `base_name` varchar(255) DEFAULT NULL,
    `base_value` decimal(15,6) NOT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `name` varchar(255) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `api_unit_id` smallint NOT NULL,
    PRIMARY KEY (`id_type_unit`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: product
CREATE TABLE IF NOT EXISTS `product` (
                                         `id_product` bigint NOT NULL AUTO_INCREMENT,
                                         `barcode` varchar(100) NOT NULL,
    `bundle` tinyint(1) DEFAULT '0',
    `created_at` datetime(6) NOT NULL,
    `image` mediumblob,
    `name` varchar(100) NOT NULL,
    `reference` varchar(100) NOT NULL,
    `service` tinyint(1) DEFAULT '0',
    `updated_at` datetime(6) DEFAULT NULL,
    `variable_price` tinyint(1) DEFAULT '0',
    `barcode_type_id` smallint DEFAULT NULL,
    `category_id` bigint DEFAULT NULL,
    `type_unit_id` smallint DEFAULT NULL,
    PRIMARY KEY (`id_product`),
    KEY `FKi7dxc4eo2cvoowbg2y3sbsbap` (`barcode_type_id`),
    KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`),
    KEY `FKnkb0a46jabamen1wqss0wwfl2` (`type_unit_id`),
    CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id_category`),
    CONSTRAINT `FKi7dxc4eo2cvoowbg2y3sbsbap` FOREIGN KEY (`barcode_type_id`) REFERENCES `barcode_type` (`id_barcode_type`),
    CONSTRAINT `FKnkb0a46jabamen1wqss0wwfl2` FOREIGN KEY (`type_unit_id`) REFERENCES `type_unit` (`id_type_unit`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: product_branch
CREATE TABLE IF NOT EXISTS `product_branch` (
                                                `id_product_branch` bigint NOT NULL AUTO_INCREMENT,
                                                `commission_type` varchar(1) DEFAULT NULL,
    `created_at` datetime(6) NOT NULL,
    `printer_number` tinyint DEFAULT NULL,
    `quantity` decimal(15,6) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `value_commission` decimal(15,6) DEFAULT NULL,
    `visible` tinyint(1) DEFAULT '1',
    `branch_id` bigint NOT NULL,
    `product_id` bigint NOT NULL,
    PRIMARY KEY (`id_product_branch`),
    KEY `FKexcwsjgcmm5e23mfok7qak1he` (`branch_id`),
    KEY `FKq3ltxgqyxw7x6tba12427115f` (`product_id`),
    CONSTRAINT `FKexcwsjgcmm5e23mfok7qak1he` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id_branch`),
    CONSTRAINT `FKq3ltxgqyxw7x6tba12427115f` FOREIGN KEY (`product_id`) REFERENCES `product` (`id_product`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: product_branch_batch
CREATE TABLE IF NOT EXISTS `product_branch_batch` (
                                                      `id_product_branch_batch` bigint NOT NULL AUTO_INCREMENT,
                                                      `batch_code` varchar(20) DEFAULT NULL,
    `created_at` datetime(6) NOT NULL,
    `entry_date` date DEFAULT NULL,
    `expedition_date` date DEFAULT NULL,
    `expiration_date` date DEFAULT NULL,
    `quantity` decimal(15,6) DEFAULT NULL,
    `serial_number` varchar(20) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `product_branch_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id_product_branch_batch`),
    KEY `FKffdkatxuj4bqlpbbjw7gj15qm` (`product_branch_id`),
    CONSTRAINT `FKffdkatxuj4bqlpbbjw7gj15qm` FOREIGN KEY (`product_branch_id`) REFERENCES `product_branch` (`id_product_branch`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: price_condition
CREATE TABLE IF NOT EXISTS `price_condition` (
                                                 `id_price_condition` smallint NOT NULL,
                                                 `created_at` datetime(6) DEFAULT NULL,
    `description` varchar(255) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id_price_condition`),
    UNIQUE KEY `UKpr9yhsbbg9k1cbyn7ij69mvi5` (`description`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: price_type
CREATE TABLE IF NOT EXISTS `price_type` (
                                            `id_price_type` smallint NOT NULL,
                                            `created_at` datetime(6) DEFAULT NULL,
    `name` varchar(255) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id_price_type`),
    UNIQUE KEY `UKrbais9xgi0yecy3qc3lbhtge2` (`name`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: supplier
CREATE TABLE IF NOT EXISTS `supplier` (
                                          `id_supplier` bigint NOT NULL AUTO_INCREMENT,
                                          `address` varchar(100) DEFAULT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `credit_limit` decimal(15,6) DEFAULT NULL,
    `identification_number` varchar(15) DEFAULT NULL,
    `last_names` varchar(100) DEFAULT NULL,
    `mail` varchar(100) DEFAULT NULL,
    `names` varchar(100) DEFAULT NULL,
    `phone` varchar(50) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `visible` tinyint(1) DEFAULT '1',
    `branch_id` bigint NOT NULL,
    `identification_type_id` smallint DEFAULT NULL,
    `municipality_id` bigint DEFAULT NULL,
    `type_organization_id` smallint DEFAULT NULL,
    `type_regime_id` smallint DEFAULT NULL,
    `type_responsibility_id` smallint DEFAULT NULL,
    PRIMARY KEY (`id_supplier`),
    KEY `FKeg0jjo8p3wwawp5qcylbqtq5b` (`branch_id`),
    KEY `FK9uptp04mcl5re18eo7to7kkyd` (`identification_type_id`),
    KEY `FK8yourdt3c0h8ya6umc35d59j6` (`municipality_id`),
    KEY `FKe84nche1xyk6p47afxuhhatra` (`type_organization_id`),
    KEY `FK1p4sa00aes93wtqqvv31xbd30` (`type_regime_id`),
    KEY `FKo75evjxixs59lg8i20tfd8p18` (`type_responsibility_id`),
    CONSTRAINT `FK1p4sa00aes93wtqqvv31xbd30` FOREIGN KEY (`type_regime_id`) REFERENCES `type_regime` (`id_type_regime`),
    CONSTRAINT `FK8yourdt3c0h8ya6umc35d59j6` FOREIGN KEY (`municipality_id`) REFERENCES `municipality` (`id_municipality`),
    CONSTRAINT `FK9uptp04mcl5re18eo7to7kkyd` FOREIGN KEY (`identification_type_id`) REFERENCES `identification_type` (`id_identification_type`),
    CONSTRAINT `FKe84nche1xyk6p47afxuhhatra` FOREIGN KEY (`type_organization_id`) REFERENCES `type_organization` (`id_type_organization`),
    CONSTRAINT `FKeg0jjo8p3wwawp5qcylbqtq5b` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id_branch`),
    CONSTRAINT `FKo75evjxixs59lg8i20tfd8p18` FOREIGN KEY (`type_responsibility_id`) REFERENCES `type_responsibility` (`id_type_responsibility`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Table: product_branch_price
CREATE TABLE IF NOT EXISTS `product_branch_price` (
                                                      `id_product_branch_price` bigint NOT NULL AUTO_INCREMENT,
                                                      `created_at` datetime(6) NOT NULL,
    `end_date` datetime(6) DEFAULT NULL,
    `minimum_quantity` decimal(15,6) DEFAULT NULL,
    `purchase_price` decimal(15,6) DEFAULT NULL,
    `sale_price` decimal(15,6) DEFAULT NULL,
    `start_date` datetime(6) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `price_condition_id` smallint DEFAULT NULL,
    `price_type_id` smallint DEFAULT NULL,
    `product_branch_id` bigint NOT NULL,
    `supplier_id` bigint DEFAULT NULL,
    `default_price` tinyint(1) DEFAULT '0',
    PRIMARY KEY (`id_product_branch_price`),
    KEY `FKctmpps9j177p2my01t0qmfr7o` (`price_condition_id`),
    KEY `FKeu9y7ex3vvhp5stkvlfbxrbj2` (`price_type_id`),
    KEY `FKg79beonivqljktg276j8bd96v` (`product_branch_id`),
    KEY `FKf0eso3ctlruu7d1u06863mpmn` (`supplier_id`),
    CONSTRAINT `FKctmpps9j177p2my01t0qmfr7o` FOREIGN KEY (`price_condition_id`) REFERENCES `price_condition` (`id_price_condition`),
    CONSTRAINT `FKeu9y7ex3vvhp5stkvlfbxrbj2` FOREIGN KEY (`price_type_id`) REFERENCES `price_type` (`id_price_type`),
    CONSTRAINT `FKf0eso3ctlruu7d1u06863mpmn` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id_supplier`),
    CONSTRAINT `FKg79beonivqljktg276j8bd96v` FOREIGN KEY (`product_branch_id`) REFERENCES `product_branch` (`id_product_branch`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: tax
CREATE TABLE IF NOT EXISTS tax (
                                   id_tax SMALLINT PRIMARY KEY NOT NULL,
                                   name VARCHAR(255) NOT NULL,
    code VARCHAR(2) NOT NULL UNIQUE,
    api_tax_id SMALLINT,
    created_at DATETIME,
    updated_at DATETIME
    );

-- Table: product_bundle
CREATE TABLE IF NOT EXISTS `product_bundle` (
                                                `id_product_bundle` bigint NOT NULL AUTO_INCREMENT,
                                                `created_at` datetime(6) NOT NULL,
    `quantity` double DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `bundle_id` bigint NOT NULL,
    `product_id` bigint NOT NULL,
    PRIMARY KEY (`id_product_bundle`),
    KEY `FKnvpihocv98k5tjyufn2v4kkw9` (`bundle_id`),
    KEY `FK8s0yq969fssa38b3uhhb2w5y3` (`product_id`),
    CONSTRAINT `FK8s0yq969fssa38b3uhhb2w5y3` FOREIGN KEY (`product_id`) REFERENCES `product` (`id_product`),
    CONSTRAINT `FKnvpihocv98k5tjyufn2v4kkw9` FOREIGN KEY (`bundle_id`) REFERENCES `product` (`id_product`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: type_tax
CREATE TABLE IF NOT EXISTS `type_tax` (
                                          `id_type_tax` smallint NOT NULL,
                                          `code` varchar(2) DEFAULT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `name` varchar(255) NOT NULL,
    `rate` decimal(15,6) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `tax_id` smallint NOT NULL,
    PRIMARY KEY (`id_type_tax`),
    UNIQUE KEY `UKhbv26h0084ld3gwqms4cy5tke` (`name`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: product_tax
CREATE TABLE IF NOT EXISTS `product_tax` (
                                             `id_product_tax` bigint NOT NULL AUTO_INCREMENT,
                                             `created_at` datetime(6) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `product_id` bigint NOT NULL,
    `type_tax_id` smallint NOT NULL,
    PRIMARY KEY (`id_product_tax`),
    KEY `FKfq0mfwjkhuufdc29hc6q50n96` (`product_id`),
    KEY `FKpar6wnshvkp68dp21pkf2i7ya` (`type_tax_id`),
    CONSTRAINT `FKfq0mfwjkhuufdc29hc6q50n96` FOREIGN KEY (`product_id`) REFERENCES `product` (`id_product`),
    CONSTRAINT `FKpar6wnshvkp68dp21pkf2i7ya` FOREIGN KEY (`type_tax_id`) REFERENCES `type_tax` (`id_type_tax`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Table: customer
CREATE TABLE IF NOT EXISTS `customer` (
                                          `id_customer` bigint NOT NULL AUTO_INCREMENT,
                                          `address` varchar(100) DEFAULT NULL,
    `amount_debt` decimal(15,6) DEFAULT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `debt_date` datetime(6) DEFAULT NULL,
    `identification_number` varchar(15) DEFAULT NULL,
    `last_names` varchar(100) DEFAULT NULL,
    `mail` varchar(100) DEFAULT NULL,
    `max_debt` decimal(15,6) NOT NULL,
    `names` varchar(100) DEFAULT NULL,
    `phone` varchar(50) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `visible` tinyint(1) DEFAULT '1',
    `branch_id` bigint NOT NULL,
    `identification_type_id` smallint DEFAULT NULL,
    `municipality_id` bigint DEFAULT NULL,
    `type_organization_id` smallint DEFAULT NULL,
    `type_regime_id` smallint DEFAULT NULL,
    `type_responsibility_id` smallint DEFAULT NULL,
    PRIMARY KEY (`id_customer`),
    KEY `FKehjjh2rstm0jx7kpw0gwub4id` (`branch_id`),
    KEY `FK6a5vvglt4p4uic4m95not8di8` (`identification_type_id`),
    KEY `FKc7m1ikxd4q9ifhb0ll30q1jwv` (`municipality_id`),
    KEY `FKovgxdakseliuogv4faa7xh4ha` (`type_organization_id`),
    KEY `FKaudvhshx2karakk6rhx52h5bx` (`type_regime_id`),
    KEY `FKgnc0036hnuxycqvwchot4m9sd` (`type_responsibility_id`),
    CONSTRAINT `FK6a5vvglt4p4uic4m95not8di8` FOREIGN KEY (`identification_type_id`) REFERENCES `identification_type` (`id_identification_type`),
    CONSTRAINT `FKaudvhshx2karakk6rhx52h5bx` FOREIGN KEY (`type_regime_id`) REFERENCES `type_regime` (`id_type_regime`),
    CONSTRAINT `FKc7m1ikxd4q9ifhb0ll30q1jwv` FOREIGN KEY (`municipality_id`) REFERENCES `municipality` (`id_municipality`),
    CONSTRAINT `FKehjjh2rstm0jx7kpw0gwub4id` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id_branch`),
    CONSTRAINT `FKgnc0036hnuxycqvwchot4m9sd` FOREIGN KEY (`type_responsibility_id`) REFERENCES `type_responsibility` (`id_type_responsibility`),
    CONSTRAINT `FKovgxdakseliuogv4faa7xh4ha` FOREIGN KEY (`type_organization_id`) REFERENCES `type_organization` (`id_type_organization`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: permission
CREATE TABLE IF NOT EXISTS `permission` (
                                            `id_permission` smallint NOT NULL,
                                            `created_at` datetime(6) DEFAULT NULL,
    `name` varchar(100) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `parent_id` smallint DEFAULT NULL,
    PRIMARY KEY (`id_permission`),
    UNIQUE KEY `UK2ojme20jpga3r4r79tdso17gi` (`name`),
    KEY `FKj8b47vqnucijscjac9jcca9gi` (`parent_id`),
    CONSTRAINT `FKj8b47vqnucijscjac9jcca9gi` FOREIGN KEY (`parent_id`) REFERENCES `permission` (`id_permission`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: role
CREATE TABLE IF NOT EXISTS `role` (
                                      `id_role` smallint NOT NULL,
                                      `created_at` datetime(6) DEFAULT NULL,
    `name` varchar(100) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id_role`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: role_permission
CREATE TABLE IF NOT EXISTS `role_permission` (
                                                 `branch_id` bigint NOT NULL,
                                                 `permission_id` smallint NOT NULL,
                                                 `role_id` smallint NOT NULL,
                                                 PRIMARY KEY (`branch_id`,`permission_id`,`role_id`),
    KEY `FKf8yllw1ecvwqy3ehyxawqa1qp` (`permission_id`),
    KEY `FKa6jx8n8xkesmjmv6jqug6bg68` (`role_id`),
    CONSTRAINT `FKa6jx8n8xkesmjmv6jqug6bg68` FOREIGN KEY (`role_id`) REFERENCES `role` (`id_role`),
    CONSTRAINT `FKf8yllw1ecvwqy3ehyxawqa1qp` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id_permission`),
    CONSTRAINT `FKsg6e2tgjtmr12vuc8626ifdup` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id_branch`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: employee
CREATE TABLE IF NOT EXISTS `employee` (
                                          `id_employee` bigint NOT NULL AUTO_INCREMENT,
                                          `address` varchar(100) DEFAULT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `identification_number` varchar(15) NOT NULL,
    `last_names` varchar(100) DEFAULT NULL,
    `mail` varchar(100) DEFAULT NULL,
    `names` varchar(100) NOT NULL,
    `password` varchar(100) DEFAULT NULL,
    `phone` varchar(100) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `visible` tinyint(1) DEFAULT '1',
    `branch_id` bigint NOT NULL,
    `identification_type_id` smallint DEFAULT NULL,
    `municipality_id` bigint DEFAULT NULL,
    `role_id` smallint NOT NULL,
    PRIMARY KEY (`id_employee`),
    UNIQUE KEY `UKn8nfa84jcv9wlfnkyypkto9gg` (`identification_number`),
    UNIQUE KEY `UK6nit1ynrjkrd1hp1c73d60rn3` (`mail`),
    UNIQUE KEY `UKbuf2qp04xpwfp5qq355706h4a` (`phone`),
    KEY `FKcvhlsx8tao1rxt7mpxrot61jt` (`branch_id`),
    KEY `FKgvuaxv5n0idlkcfjsh7qd7xar` (`identification_type_id`),
    KEY `FKmlt6whku7ei7g9pr604n4h0q9` (`municipality_id`),
    KEY `FK3046kvjyysq288vy3lsbtc9nw` (`role_id`),
    CONSTRAINT `FK3046kvjyysq288vy3lsbtc9nw` FOREIGN KEY (`role_id`) REFERENCES `role` (`id_role`),
    CONSTRAINT `FKcvhlsx8tao1rxt7mpxrot61jt` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id_branch`),
    CONSTRAINT `FKgvuaxv5n0idlkcfjsh7qd7xar` FOREIGN KEY (`identification_type_id`) REFERENCES `identification_type` (`id_identification_type`),
    CONSTRAINT `FKmlt6whku7ei7g9pr604n4h0q9` FOREIGN KEY (`municipality_id`) REFERENCES `municipality` (`id_municipality`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: invoice
CREATE TABLE IF NOT EXISTS `invoice` (
                                         `id_invoice` bigint NOT NULL AUTO_INCREMENT,
                                         `created_at` datetime(6) NOT NULL,
    `date` datetime(6) NOT NULL,
    `discount_type` varchar(1) DEFAULT NULL,
    `observation` varchar(1000) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `value_discount` decimal(15,6) NOT NULL,
    `value_service_change` decimal(2,1) NOT NULL,
    `cash_id` bigint NOT NULL,
    `commission_type` varchar(1) DEFAULT NULL,
    `subtotal` decimal(15,6) NOT NULL,
    `table_name` varchar(50) DEFAULT NULL,
    `total` decimal(15,6) NOT NULL,
    `total_commission` decimal(15,6) NOT NULL,
    `total_discount` decimal(15,6) NOT NULL,
    `total_retention` decimal(15,6) NOT NULL,
    `total_service_change` decimal(15,6) NOT NULL,
    `total_tax` decimal(15,6) NOT NULL,
    `value_commission` decimal(15,6) DEFAULT NULL,
    PRIMARY KEY (`id_invoice`),
    KEY `FKn6ws3bkjod5c5ymw4pj4r2ndl` (`cash_id`),
    CONSTRAINT `FKn6ws3bkjod5c5ymw4pj4r2ndl` FOREIGN KEY (`cash_id`) REFERENCES `cash` (`id_cash`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: invoice_customer
CREATE TABLE IF NOT EXISTS `invoice_customer` (
                                                  `id_invoice_customer` bigint NOT NULL AUTO_INCREMENT,
                                                  `address` varchar(100) DEFAULT NULL,
    `created_at` datetime(6) NOT NULL,
    `identification_number` varchar(15) DEFAULT NULL,
    `last_names` varchar(100) DEFAULT NULL,
    `mail` varchar(100) DEFAULT NULL,
    `names` varchar(100) NOT NULL,
    `phone` varchar(15) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `identification_type_id` smallint DEFAULT NULL,
    `invoice_id` bigint NOT NULL,
    `municipality_id` bigint DEFAULT NULL,
    `type_organization_id` smallint DEFAULT NULL,
    `type_regime_id` smallint DEFAULT NULL,
    `type_responsibility_id` smallint DEFAULT NULL,
    PRIMARY KEY (`id_invoice_customer`),
    KEY `FK9o23yl3godova7o17sfjte0mp` (`identification_type_id`),
    KEY `FK2mbhnnuh8phv0oaldau11iklj` (`invoice_id`),
    KEY `FKhmsortxyrvxqy59aosius874e` (`municipality_id`),
    KEY `FKhwbl1nmua4khbi35t4bk2kc7u` (`type_organization_id`),
    KEY `FKnkfm8voahtri10yv1787ea7o8` (`type_regime_id`),
    KEY `FKsvg9as90qotl0l1lpafxj628r` (`type_responsibility_id`),
    CONSTRAINT `FK2mbhnnuh8phv0oaldau11iklj` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id_invoice`),
    CONSTRAINT `FK9o23yl3godova7o17sfjte0mp` FOREIGN KEY (`identification_type_id`) REFERENCES `identification_type` (`id_identification_type`),
    CONSTRAINT `FKhmsortxyrvxqy59aosius874e` FOREIGN KEY (`municipality_id`) REFERENCES `municipality` (`id_municipality`),
    CONSTRAINT `FKhwbl1nmua4khbi35t4bk2kc7u` FOREIGN KEY (`type_organization_id`) REFERENCES `type_organization` (`id_type_organization`),
    CONSTRAINT `FKnkfm8voahtri10yv1787ea7o8` FOREIGN KEY (`type_regime_id`) REFERENCES `type_regime` (`id_type_regime`),
    CONSTRAINT `FKsvg9as90qotl0l1lpafxj628r` FOREIGN KEY (`type_responsibility_id`) REFERENCES `type_responsibility` (`id_type_responsibility`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: invoice_employee
CREATE TABLE IF NOT EXISTS `invoice_employee` (
                                                  `id_invoice_employee` bigint NOT NULL AUTO_INCREMENT,
                                                  `created_at` datetime(6) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `employee_id` bigint NOT NULL,
    `invoice_id` bigint NOT NULL,
    PRIMARY KEY (`id_invoice_employee`),
    KEY `FKantoehi1g2g338qtn8lhhyly6` (`employee_id`),
    KEY `FKdohrbr38hhgdeqjmx9fuipi0v` (`invoice_id`),
    CONSTRAINT `FKantoehi1g2g338qtn8lhhyly6` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id_employee`),
    CONSTRAINT `FKdohrbr38hhgdeqjmx9fuipi0v` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id_invoice`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS payment_form (
                                            id_payment_form SMALLINT PRIMARY KEY NOT NULL,
                                            name VARCHAR(15),
    code VARCHAR(2),
    created_at DATETIME,
    updated_at DATETIME
    );

-- Table: type_payment
CREATE TABLE IF NOT EXISTS `type_payment` (
                                              `id_type_payment` smallint NOT NULL,
                                              `created_at` datetime(6) DEFAULT NULL,
    `name` varchar(100) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `api_payment_id` smallint NOT NULL,
    PRIMARY KEY (`id_type_payment`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: invoice_payment
CREATE TABLE IF NOT EXISTS `invoice_payment` (
                                                 `id_invoice_payment` bigint NOT NULL AUTO_INCREMENT,
                                                 `created_at` datetime(6) NOT NULL,
    `total_change` decimal(15,6) DEFAULT NULL,
    `total_received` decimal(15,6) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `invoice_id` bigint NOT NULL,
    `type_payment_id` smallint NOT NULL,
    PRIMARY KEY (`id_invoice_payment`),
    KEY `FKkopeu965ps1ljahtib8n8nub2` (`invoice_id`),
    KEY `FK5mls3pfs6p98jn1artj5pxdmi` (`type_payment_id`),
    CONSTRAINT `FK5mls3pfs6p98jn1artj5pxdmi` FOREIGN KEY (`type_payment_id`) REFERENCES `type_payment` (`id_type_payment`),
    CONSTRAINT `FKkopeu965ps1ljahtib8n8nub2` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id_invoice`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: invoice_product
CREATE TABLE IF NOT EXISTS `invoice_product` (
                                                 `id_invoice_product` bigint NOT NULL AUTO_INCREMENT,
                                                 `barcode` varchar(50) NOT NULL,
    `is_bundle` tinyint(1) DEFAULT '0',
    `category_name` varchar(100) DEFAULT NULL,
    `commission_type` varchar(1) DEFAULT NULL,
    `created_at` datetime(6) NOT NULL,
    `discount_type` varchar(1) DEFAULT NULL,
    `name` varchar(100) NOT NULL,
    `observation` varchar(1000) DEFAULT NULL,
    `product_stock_id` bigint DEFAULT NULL,
    `purchase_price` decimal(15,6) DEFAULT NULL,
    `quantity` decimal(15,6) NOT NULL,
    `reference` varchar(50) NOT NULL,
    `sale_price` decimal(15,6) NOT NULL,
    `is_service` tinyint(1) DEFAULT '0',
    `updated_at` datetime(6) DEFAULT NULL,
    `value_commission` decimal(15,6) DEFAULT NULL,
    `value_discount` decimal(15,6) DEFAULT NULL,
    `variable_price` tinyint(1) DEFAULT '0',
    `invoice_id` bigint NOT NULL,
    `type_unit_id` smallint NOT NULL,
    `final_price` decimal(15,6) NOT NULL,
    `subtotal` decimal(15,6) NOT NULL,
    `total` decimal(15,6) NOT NULL,
    `total_commission` decimal(15,6) NOT NULL,
    `total_discount` decimal(15,6) NOT NULL,
    `total_retention` decimal(15,6) NOT NULL,
    `total_tax` decimal(15,6) NOT NULL,
    PRIMARY KEY (`id_invoice_product`),
    KEY `FKhrqne4uostar9vds76ynsosov` (`invoice_id`),
    KEY `FKbyhdfykyjsvdlbiwsr5hlc1yc` (`type_unit_id`),
    CONSTRAINT `FKbyhdfykyjsvdlbiwsr5hlc1yc` FOREIGN KEY (`type_unit_id`) REFERENCES `type_unit` (`id_type_unit`),
    CONSTRAINT `FKhrqne4uostar9vds76ynsosov` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id_invoice`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: invoice_product_employee
CREATE TABLE IF NOT EXISTS `invoice_product_employee` (
                                                          `id_invoice_product_employee` bigint NOT NULL AUTO_INCREMENT,
                                                          `created_at` datetime(6) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `employee_id` bigint NOT NULL,
    `invoice_product_id` bigint NOT NULL,
    PRIMARY KEY (`id_invoice_product_employee`),
    KEY `FKaj5x53oxaccl726ou18kce6xu` (`employee_id`),
    KEY `FKk25pjf13wijebrobtld7nygpa` (`invoice_product_id`),
    CONSTRAINT `FKaj5x53oxaccl726ou18kce6xu` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id_employee`),
    CONSTRAINT `FKk25pjf13wijebrobtld7nygpa` FOREIGN KEY (`invoice_product_id`) REFERENCES `invoice_product` (`id_invoice_product`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: invoice_product_tax
CREATE TABLE IF NOT EXISTS `invoice_product_tax` (
                                                     `id_invoice_product_tax` bigint NOT NULL AUTO_INCREMENT,
                                                     `created_at` datetime(6) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `invoice_product_id` bigint NOT NULL,
    `type_tax_id` smallint NOT NULL,
    PRIMARY KEY (`id_invoice_product_tax`),
    KEY `FKksgrog5e4gk8vv5ju8ud1dhm5` (`invoice_product_id`),
    KEY `FK8op3sx6qhbgou72pbqtmo84c8` (`type_tax_id`),
    CONSTRAINT `FK8op3sx6qhbgou72pbqtmo84c8` FOREIGN KEY (`type_tax_id`) REFERENCES `type_tax` (`id_type_tax`),
    CONSTRAINT `FKksgrog5e4gk8vv5ju8ud1dhm5` FOREIGN KEY (`invoice_product_id`) REFERENCES `invoice_product` (`id_invoice_product`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: invoice_tax
CREATE TABLE IF NOT EXISTS `invoice_tax` (
                                             `id_invoice_tax` bigint NOT NULL AUTO_INCREMENT,
                                             `created_at` datetime(6) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `invoice_id` bigint NOT NULL,
    `type_tax_id` smallint NOT NULL,
    PRIMARY KEY (`id_invoice_tax`),
    KEY `FKgk5hrmlyhaq5hmvubpljyq21s` (`invoice_id`),
    KEY `FKddnx7hgqhh6qj8yedxky6f5lo` (`type_tax_id`),
    CONSTRAINT `FKddnx7hgqhh6qj8yedxky6f5lo` FOREIGN KEY (`type_tax_id`) REFERENCES `type_tax` (`id_type_tax`),
    CONSTRAINT `FKgk5hrmlyhaq5hmvubpljyq21s` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id_invoice`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: type_electronic_document
CREATE TABLE IF NOT EXISTS `type_electronic_document` (
                                                          `id_type_electronic_document` smallint NOT NULL,
                                                          `code` varchar(2) DEFAULT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `name` varchar(100) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `api_electronic_document_id` smallint NOT NULL,
    PRIMARY KEY (`id_type_electronic_document`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table: sales_pending
CREATE TABLE IF NOT EXISTS `sales_pending` (
                                               `id_sale_pending` varchar(36) NOT NULL,
    `created_at` datetime(6) NOT NULL,
    `customer_address` varchar(150) DEFAULT NULL,
    `customer_name` varchar(100) DEFAULT NULL,
    `customer_phone` varchar(15) DEFAULT NULL,
    `date` datetime(6) NOT NULL,
    `is_delivery` bit(1) NOT NULL,
    `domiciliary_name` varchar(100) DEFAULT NULL,
    `employee_name` varchar(100) NOT NULL,
    `invoice` text NOT NULL,
    `total` decimal(15,6) NOT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    `branch_id` bigint NOT NULL,
    `table_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id_sale_pending`),
    KEY `FKm93p02jclj0hs9uy4mne2kaqt` (`branch_id`),
    KEY `FKtlwp7wo7u4g5k7awb9oij65ex` (`table_id`),
    CONSTRAINT `FKm93p02jclj0hs9uy4mne2kaqt` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id_branch`),
    CONSTRAINT `FKtlwp7wo7u4g5k7awb9oij65ex` FOREIGN KEY (`table_id`) REFERENCES `tabless` (`id_table`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;