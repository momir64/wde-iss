-- ROLES
INSERT INTO role (id,name)
VALUES
    ('6e569715-d341-4431-8370-d7516907d2e2','ROLE_ORGANIZER'),
    ('9150703f-0449-40e1-b1fb-f0cb0a31b7b0','ROLE_GUEST'),
    ('4e3484cf-382f-448d-a1db-1c1063ab41ee','ROLE_SELLER'),
    ('7ed06959-1018-406f-b3c1-2e91c08daa07','ROLE_ADMIN');

-- CITIES
INSERT INTO city (name)
VALUES
    ('Ada'),('Adaševci'),('Adorjan'),('Adrani'),('Aleksa Šantić'),('Aleksandrovac'),('Aleksandrovo'),('Aleksinac'),('Aleksinački Rudnik'),('Alibunar'),('Aljinovići'),('Apatin'),('Aradac'),('Aranđelovac'),('Arilje'),('Azanja'),('Ašanja'),('Babušnica'),('Badnjevac'),('Badovinci'),('Bagrdan'),('Bajina Bašta'),('Bajmok'),('Bajša'),('Balajnac'),('Baljevac'),('Banatska Dubica'),('Banatska Palanka'),('Banatska Subotica'),('Banatska Topola'),('Banatski Brestovac'),('Banatski Despotovac'),('Banatski Dvor'),
    ('Banatski Karlovac'),('Banatsko Aranđelovo'),('Banatsko Karađorđevo'),('Banatsko Novo Selo'),('Banatsko Veliko Selo'),('Banja (kod Priboja)'),('Banja Koviljača'),('Banja'),('Banjani'),('Banjska'),('Banovci-Dunav'),('Banovo Polje'),('Banoštor'),('Barajevo'),('Baranda'),('Barbatovac'),('Bare'),('Barice'),('Barič'),('Barlovo'),('Baroševac'),('Batajnica'),('Batočina'),('Batrovci'),('Batuse'),('Bavanište'),('Bač'),('Bačevci'),('Bačina'),('Bačinci'),('Bačka Palanka'),('Bačka Topola'),('Bački Breg'),('Bački Brestovac'),
    ('Bački Gračac'),('Bački Jarak'),('Bački Monoštor'),('Bački Petrovac'),('Bački Sokolac'),('Bački Vinogradi'),('Bačko Dobro Polje'),('Bačko Gradište'),('Bačko Novo Selo'),('Bačko Petrovo Selo'),('Bašaid'),('Begeč'),('Bela Crkva'),('Bela Palanka'),('Bela Voda'),('Bela Zemlja'),('Belanovica'),('Bele Vode'),('Belegiš'),('Beli Potok'),('Beljina'),('Belo Blato'),('Beloljin'),('Belosavci'),('Belotinac'),('Beloševac'),('Belušić'),('Beograd'),('Beočin'),('Berkasovo'),('Bezdan'),('Bečej'),('Bešenovo'),('Beška'),
    ('Bigrenica'),('Bikić Do'),('Bikovo'),('Biljača'),('Bingula'),('Bioska'),('Bistar'),('Bistrica'),('Blace'),('Blaževo'),('Bobovo'),('Bogaraš'),('Bogatić'),('Bogojevce'),('Bogojevo'),('Bogovađa'),('Bogovina'),('Bogutovac'),('Bojnik'),('Boka'),('Boljevac'),('Boljkovci'),('Bor'),('Borski Brestovac'),('Bosilegrad'),('Bosut'),('Botoš'),('Bočar'),('Bođani'),('Bošnjace'),('Bošnjane'),('Božetići'),('Boževac'),('Božica'),('Bradarac'),('Braničevo'),('Brankovina'),('Bratljevo'),('Braćevac'),('Brekovo'),('Bresnica'),('Brestovac'),('Brestovačka Banja'),
    ('Brezjak'),('Brezna'),('Brezova'),('Brezovica'),('Brežđe'),('Brgule'),('Brodarevo'),('Brus'),('Brusnik'),('Brvenik'),('Brza Palanka'),('Brzan'),('Brzeće'),('Brzi Brod'),('Brđani'),('Budisava'),('Bujanovac'),('Bukorovac'),('Bukovac'),('Bukovica'),('Bukovik'),('Bunar'),('Burdimo'),('Burovac'),('Busilovac'),('Bučje'),('Buđanovci'),('Cerovac'),('Crepaja'),('Crkvine'),('Crna Bara'),('Crna Trava'),('Crnoklište'),('Crvena Crkva'),('Crvena Reka'),('Crvenka'),('Darosava'),('Debeljača'),('Debrc'),('Deliblato'),('Delimeđe'),
    ('Deronje'),('Desimirovac'),('Despotovac'),('Despotovo'),('Devojački Bunar'),('Deč'),('Deževa'),('Dimitrovgrad'),('Divci'),('Divljaka'),('Divostin'),('Divoš'),('Divčibare'),('Dobra'),('Dobri Do'),('Dobrica'),('Dobrinci'),('Dobrić'),('Dolac'),('Doljevac'),('Dolovo'),('Donja Bela Reka'),('Donja Borina'),('Donja Gušterica'),('Donja Kamenica'),('Donja Ljubata'),('Donja Mutnica'),('Donja Orovica'),('Donja Rečica'),('Donja Trnava'),('Donja Šatornja'),('Donje Crnatovo'),('Donje Crniljevo'),('Donje Međurovo'),('Donje Vidovo'),('Donje Zuniče'),
    ('Donji Dušnik'),('Donji Krčin'),('Donji Milanovac'),('Donji Stajevac'),('Doroslovo'),('Draginac'),('Draginje'),('Draglica'),('Dragobraća'),('Dragocvet'),('Dragolj'),('Dragovo'),('Dragoševac'),('Drajkovce'),('Dračić'),('Draževac'),('Drenovac'),('Drugovac'),('Dublje'),('Duboka'),('Dubovac'),('Dubovo'),('Dubočane'),('Dubravica'),('Dudovica'),('Duga Poljana'),('Dugo Polje'),('Dupljaja'),('Džep'),('Džigolj'),('Elemir'),('Erdevik'),('Erdeč'),('Ečka'),('Farkaždin'),('Feketić'),('Futog'),('Gadžin Han'),
    ('Gaj'),('Gajdobra'),('Gakovo'),('Gamzigradska Banja'),('Gardinovci'),('Gibarac'),('Glavinci'),('Globoder'),('Glogonj'),('Glogovac'),('Gložan'),('Glušci'),('Gnjilan'),('Golobok'),('Golubac'),('Golubinci'),('Goračići'),('Gornja Dobrinja'),('Gornja Draguša'),('Gornja Lisina'),('Gornja Sabanta'),('Gornja Toplica'),('Gornja Toponica'),('Gornja Trepča'),('Gornja Trnava'),('Gornje Dvorane'),('Gornji Barbeš'),('Gornji Breg'),('Gornji Matejevac'),('Gornji Milanovac'),('Gornji Stepoš'),('Gornji Stupanj'),('Gospođinci'),('Gostun'),('Grabovac'),
    ('Grabovci'),('Grabovica'),('Gradskovo'),('Gračanica'),('Grdelica'),('Grebenac'),('Grgurevci'),('Grlište'),('Grljan'),('Grocka'),('Grošnica'),('Gruža'),('Guberevac'),('Gudurica'),('Gunaroš'),('Guča'),('Guševac'),('Hajdukovo'),('Hajdučica'),('Hetin'),('Horgoš granični prelaz'),('Horgoš'),('Hrtkovci'),('Idvor'),('Ilandža'),('Ilinci'),('Ilićevo'),('Inđija'),('Irig'),('Ivanjica'),('Ivanovo'),('Izbište'),('Iđoš'),('Jabuka'),('Jabukovac'),('Jabučje'),('Jadranska Lešnica'),('Jagnjilo'),('Jagodina'),('Jamena'),
    ('Jankov Most'),('Janošik'),('Jarak'),('Jarkovac'),('Jarmenovci'),('Jasenovo'),('Jasika'),('Jasikovica'),('Jasikovo'),('Jazak'),('Jazovo'),('Jaša Tomić'),('Jelašnica'),('Jelen Do'),('Jelovik'),('Jermenovci'),('Ježevica'),('Jovac'),('Jovanovac'),('Jošanica'),('Jošanička Banja'),('Jugbogdanovac'),('Junkovac'),('Kalna'),('Kaluđerica'),('Kamenica'),('Kanjiža'),('Kaona'),('Kaonik'),('Karan'),('Karavukovo'),('Karađorđevo'),('Karlovčić'),('Katun'),('Kać'),('Kačarevo'),('Kelebija gran. prelaz'),('Kelebija'),('Kevi'),('Kikinda'),
    ('Kisač'),('Kladovo'),('Klek'),('Klenak'),('Klenike'),('Klenje'),('Klisura'),('Kličevac'),('Kljajićevo'),('Klokočevac'),('Knić'),('Knićanin'),('Knjaževac'),('Kobišnica'),('Koceljeva'),('Kokin Brod'),('Kolare'),('Kolari'),('Kolut'),('Komirić'),('Konak'),('Konarevo'),('Konjuh'),('Končarevo'),('Kopaonik'),('Koprivnica'),('Koraćica'),('Korbevac'),('Korbovo'),('Korenita'),('Korman'),('Kosančić'),('Kosjerić'),('Kosovo Polje'),('Kosovska Kamenica'),('Kosovska Mitrovica'),('Kostojevići'),('Kostolac'),('Kotraža'),('Kovačevac'),
    ('Kovačica'),('Kovilj'),('Kovin'),('Kragujevac'),('Krajišnik'),('Kraljevci'),('Kraljevo'),('Kremna'),('Krepoljin'),('Kriva Feja'),('Krivaja'),('Krivelj'),('Krivi Vir'),('Krnjevo'),('Krnješevci'),('Krupac'),('Krupanj'),('Krušar'),('Krušedol'),('Kruševac'),('Kruševica'),('Kruščić'),('Krčedin'),('Kucura'),('Kukljin'),('Kukujevci'),('Kula'),('Kulina'),('Kulpin'),('Kumane'),('Kupci'),('Kupinik'),('Kupinovo'),('Kupusina'),('Kuršumlija'),('Kuršumlijska Banja'),('Kusadak'),('Kusić'),('Kuzmin'),('Kučevo'),('Kušiljevo'),
    ('Kušići'),('Kuštilj'),('Lajkovac'),('Lalinac'),('Lalić'),('Laplje Selo'),('Lapovo (Varoš)'),('Lapovo'),('Lazarevac'),('Lazarevo'),('Laznica'),('Laćarak'),('Lađevci'),('Lebane'),('Lece'),('Ledinci'),('Lelić'),('Lenovac'),('Lepenac'),('Lepenica'),('Lepina'),('Leposavić'),('Leskovac'),('Lešak'),('Lešnica'),('Ležimir'),('Lipar'),('Lipe'),('Lipnički Šor'),('Lipolist'),('Lički Hanovi'),('Ljig'),('Ljuba'),('Ljuberađa'),('Ljubiš'),('Ljubovija'),('Ljukovo'),('Ljutovo'),('Lok'),('Lokve'),('Lovćenac'),('Loznica'),('Lozovik'),
    ('Loćika'),('Lubnica'),('Lug'),('Lugavčina'),('Lukare'),('Lukino Selo'),('Lukićevo'),('Lukovo'),('Lukovska Banja'),('Lunovo Selo'),('Lučani'),('Lužnice'),('Maglić'),('Majdan'),('Majdanpek'),('Majilovac'),('Majur'),('Mala Bosna'),('Mala Krsna'),('Mala Moštanica'),('Mala Plana'),('Male Pijace'),('Male Pčelice'),('Mali Beograd'),('Mali Izvor'),('Mali Iđoš'),('Mali Jasenovac'),('Mali Požarevac'),('Mali Zvornik'),('Malo Crniće'),('Malo Krčmare'),('Malošište'),('Malča'),('Manojlovce'),('Manđelos'),('Maradik'),
    ('Margita'),('Markovac'),('Markovica'),('Martinci'),('Martonoš'),('Maršić'),('Mataruška Banja'),('Mačkat'),('Mačvanska Mitrovica'),('Mačvanski Pričinović'),('Medoševac'),('Medveđa'),('Melenci'),('Melenci-Rusanda'),('Meljak'),('Merdare'),('Merošina'),('Merćez'),('Metlić'),('Metovnica'),('Međa'),('Međulužje'),('Međurečje'),('Mihajlovac'),('Mihajlovo'),('Mijatovac'),('Milatovac'),('Milentija'),('Miletićevo'),('Mileševo'),('Miloševac'),('Miloševo'),('Milutovac'),('Minićevo'),('Mionica'),('Mirosaljci'),('Miroševce'),
    ('Mitrovac'),('Mišićevo'),('Mladenovac'),('Mladenovo'),('Mokra Gora'),('Mokrin'),('Mol'),('Molovin'),('Morović'),('Mozgovo'),('Mošorin'),('Mramor'),('Mramorak'),('Mrčajevci'),('Mršinci'),('Muhovac'),('Mužlja'),('Nadalj'),('Nakovo'),('Natalinci'),('Negotin'),('Neresnica'),('Neuzina'),('Neštin'),('Nikinci'),('Nikolinci'),('Nikoličevo'),('Niš'),('Niševac'),('Niška Banja'),('Nova Crnja'),('Nova Crvenka'),('Nova Gajdobra'),('Nova Pazova'),('Nova Varoš'),('Novi Banovci'),('Novi Beograd'),('Novi Bečej'),('Novi Bračin'),
    ('Novi Itebej'),('Novi Karlovci'),('Novi Kneževac'),('Novi Kozarci'),('Novi Kozjak'),('Novi Pazar'),('Novi Sad'),('Novi Slankamen'),('Novi Žednik'),('Novo Miloševo'),('Novo Orahovo'),('Novo Selo'),('Noćaj'),('Obrenovac'),('Obrež'),('Obrovac'),('Odžaci'),('Ogar'),('Omoljica'),('Oparić'),('Opovo'),('Orane'),('Orašac'),('Oreškovica'),('Orid'),('Orlovat'),('Orom'),('Osanica'),('Osečina'),('Osipaonica'),('Ostojićevo'),('Ostrovica'),('Ostružnica'),('Ovčar Banja'),('Oštrelj'),('Padej'),('Padež'),('Padina'),
    ('Palilula'),('Palić'),('Pambukovica'),('Panonija'),('Pančevo'),('Parage'),('Paraćin'),('Parteš'),('Parunovac'),('Pasjane'),('Pavliš'),('Pačir'),('Pecka'),('Pejkovac'),('Pepeljevac'),('Perlez'),('Perućac'),('Petlovača'),('Petrovac'),('Petrovaradin'),('Pećinci'),('Pečenjevce'),('Pinosava'),('Pirot'),('Pivnice'),('Plana'),('Plandište'),('Platičevo'),('Plavna'),('Plažane'),('Plemetina'),('Pleš'),('Pločica'),('Pobeda'),('Podgorac'),('Podunavci'),('Podvis'),('Podvrška'),('Poganovo'),('Pojate'),('Poljana'),('Poljska Ržana'),
    ('Popinci'),('Popovac'),('Popučke'),('Porodin'),('Potočac'),('Poćuta'),('Počekovina'),('Požarevac'),('Požega'),('Prahovo'),('Pranjani'),('Predejane'),('Prekonoga'),('Preljina'),('Prevešt'),('Preševo'),('Preševo-terminal'),('Prhovo'),('Priboj Vranjski'),('Priboj'),('Prigrevica'),('Prijepolje'),('Prilički Kiseljak'),('Prilužje'),('Privina Glava'),('Pričević'),('Prnjavor (Mačvanski)'),('Prokuplje'),('Prolom Banja'),('Provo'),('Pružatovac'),('Pukovac'),('Putinci'),('Rabrovo'),('Radalj'),('Radinac'),('Radičević'),
    ('Radljevo'),('Radojevo'),('Radovnica'),('Radujevac'),('Rainovača'),('Rajković'),('Rakovac'),('Rakovica'),('Ralja'),('Ranilović'),('Ranilug'),('Ranovac'),('Rastina'),('Rataje'),('Ratari'),('Ratina'),('Ratkovo'),('Ravna Dubrava'),('Ravni Topolovac'),('Ravni'),('Ravnje'),('Ravno Selo'),('Razbojna'),('Razgojna'),('Rača Kragujevačka'),('Rača'),('Rašanac'),('Raševica'),('Raška'),('Ražana'),('Ražanj'),('Rekovac'),('Resavica'),('Resnik'),('Rgotina'),('Ribare'),('Ribari'),('Ribariće'),('Ribarska Banja'),('Ripanj'),
    ('Ristovac'),('Riđica'),('Rogača'),('Rogačica'),('Roge'),('Rogljevo'),('Roćevići'),('Rožanstvo'),('Rudna Glava'),('Rudnica'),('Rudnik'),('Rudno'),('Rudovci'),('Ruma'),('Rumenka'),('Ruski Krstur'),('Rusko Selo'),('Rutevac'),('Rušanj'),('Sajan'),('Sakule'),('Salaš Noćajski'),('Salaš'),('Samaila'),('Samoš'),('Sanad'),('Saraorci'),('Sastav Reka'),('Sastavci'),('Savinac'),('Savino Selo'),('Savski venac'),('Sedlare'),('Sefkerin'),('Selenča'),('Seleuš'),('Selevac'),('Senje'),('Senjski Rudnik'),('Senta'),('Sevojno'),
    ('Seča Reka'),('Sečanica'),('Sečanj'),('Sibač'),('Sijarinska Banja'),('Sikirica'),('Sikole'),('Silbaš'),('Siokovac'),('Sip'),('Sipić'),('Sirig'),('Sirogojno'),('Sirča'),('Sivac'),('Sićevo'),('Sjenica'),('Skela'),('Skobalj'),('Skorenovac'),('Slatina'),('Slavkovica'),('Slovac'),('Smederevo'),('Smederevska Palanka'),('Smilovci'),('Smoljinac'),('Sokobanja'),('Sombor'),('Sonta'),('Sopot'),('Sot'),('Sočanica'),('Srbobran'),('Srednjevo'),('Sremska Kamenica'),('Sremska Mitrovica'),('Sremska Rača'),('Sremski Karlovci'),
    ('Sremski Mihaljevci'),('Sremčica'),('Srpska Crnja'),('Srpski Itebej'),('Srpski Krstur'),('Srpski Miletić'),('Stajićevo'),('Stalać'),('Stanišić'),('Stapar'),('Stara Moravica'),('Stara Pazova'),('Stari Banovci'),('Stari Lec'),('Stari Slankamen'),('Stari grad'),('Stari Žednik'),('Staro Selo'),('Starčevo'),('Stave'),('Stejanovci'),('Stenjevac'),('Stepanovićevo'),('Stepojevac'),('Stojnik'),('Stopanja'),('Stragari'),('Straža'),('Strelac'),('Stubal'),('Stubica'),('Stubline'),('Studenica'),('Subotica (kod Svijanca)'),
    ('Subotica'),('Subotinac'),('Subotište'),('Sukovo'),('Sumrakovac'),('Supska'),('Surduk'),('Surdulica'),('Surčin'),('Susek'),('Sutjeska'),('Sveti Ilija'),('Svetozar Miletić'),('Svilajnac'),('Svileuva'),('Svilojevo'),('Svojnovo'),('Svođe'),('Svrljig'),('Takovo'),('Taraš'),('Tavankut'),('Tekeriš'),('Tekija'),('Telečka'),('Temerin'),('Temska'),('Tešica'),('Titel'),('Toba'),('Tomaševac'),('Tomislavci'),('Topola'),('Topolovnik'),('Toponica'),('Torak'),('Torda'),('Tornjoš'),('Totovo Selo'),('Tovariševo'),('Trbušani'),('Trešnjevac'),
    ('Trešnjevica'),('Trgovište'),('Trnavci'),('Trnjane'),('Trstenik'),('Trupale'),('Tršić'),('Tulare'),('Turekovac'),('Turija'),('Tutin'),('Ub'),('Ugao'),('Ugrinovci'),('Uljma'),('Umka'),('Urovica'),('Utrine'),('Uzdin'),('Uzovnica'),('Ušće'),('Užice'),('Vajska'),('Valjevo'),('Varda'),('Varna'),('Varoš'),('Varvarin'),('Vasilj'),('Vatin'),('Vašica'),('Velebit'),('Velesnica'),('Velika Drenova'),('Velika Grabovnica'),('Velika Greda'),('Velika Ivanča'),('Velika Jasikova'),('Velika Krsna'),('Velika Lomnica'),
    ('Velika Moštanica'),('Velika Plana'),('Velika Reka'),('Velika Vrbnica'),('Veliki Borak'),('Veliki Crljeni'),('Veliki Gaj'),('Veliki Izvor'),('Veliki Popović'),('Veliki Radinci'),('Veliki Trnovac'),('Veliki Šiljegovac'),('Veliko Bonjince'),('Veliko Gradište'),('Veliko Laole'),('Veliko Orašje'),('Veliko Središte'),('Venčane'),('Veternik'),('Vilovo'),('Vina'),('Vionica'),('Visočka Ržana'),('Vitanovac'),('Vitkovac'),('Vitoševac'),('Viča'),('Višnjevac'),('Višnjićevo'),('Vladimirci'),('Vladimirovac'),('Vladičin Han'),
    ('Vlajkovac'),('Vlase'),('Vlasotince'),('Vlaška'),('Vodanj'),('Voganj'),('Vojka'),('Vojska'),('Vojvoda Stepa'),('Vojvodinci'),('Voluja'),('Voždovac'),('Vranić'),('Vranje'),('Vranjska Banja'),('Vranovo'),('Vratarnica'),('Vraćevšnica'),('Vračar'),('Vračev Gaj'),('Vražogrnac'),('Vrba'),('Vrbas'),('Vrbica'),('Vrbovac'),('Vrdnik'),('Vreoci'),('Vrhpolje'),('Vrmdža'),('Vrnjačka Banja'),('Vrnjci'),('Vrćenovica'),('Vršac'),('Vukovac'),('Vučje'),('Zablaće'),('Zabojnica'),('Zabrežje'),('Zagajica'),('Zajača'),('Zaječar'),('Zaplanjska Toponica'),
    ('Zasavica'),('Zavlaka'),('Zdravinje'),('Zemun'),('Zlatibor'),('Zlatica'),('Zlodol'),('Zlot'),('Zmajevo'),('Zminjak'),('Zrenjanin'),('Zubin Potok'),('Zuce'),('Zvezdan'),('Zvezdara'),('Zvečan'),('Zvonce'),('Ćićevac'),('Ćuprija'),('Čajetina'),('Čalma'),('Čantavir'),('Čačak'),('Čelarevo'),('Čenej'),('Čenta'),('Čerević'),('Čestereg'),('Čečina'),('Čitluk'),('Čoka'),('Čonoplja'),('Čortanovci'),('Čukarica'),('Čukojevac'),('Čumić'),('Čurug'),('Đala'),('Đunis'),('Đurđevo'),('Đurđin'),('Šabac'),('Šajkaš'),('Šarbanovac'),
    ('Šarbanovac-Timok'),('Šašinci'),('Šepšin'),('Šetonje'),('Šid'),('Šilovo'),('Šimanovci'),('Šljivovac'),('Šljivovica'),('Šljivovo'),('Štavalj'),('Štitar'),('Štitare'),('Štrpce'),('Štubik'),('Šupljak'),('Šurjan'),('Žabalj'),('Žabari'),('Žagubica'),('Željuše'),('Žirovnica'),('Žitište'),('Žitkovac'),('Žitni Potok'),('Žitorađa'),('Žiča'),('Žuč');

-- PROFILE
INSERT INTO profile (id, are_notifications_muted, email, is_active, is_verified, password, role_id, image_name)
VALUES
    -- PROFILES OF ADMINS
    ('7a92c4d7-8742-4a25-aee8-750ca987b42e', true, 'john.doe@example.com', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','7ed06959-1018-406f-b3c1-2e91c08daa07', '1b385172-3928-4691-9759-d51d2a02b0bf'),
    ('b2d2e1f0-06ae-4b0f-9cd0-7b5b31b1e9fe', false, 'alice.lee@example.com', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','7ed06959-1018-406f-b3c1-2e91c08daa07', '1b385172-3928-4691-9759-d51d2a02b0bf'),
    ('f591021a-5b30-491f-a87f-02d4bec6d508', false, 'a', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','7ed06959-1018-406f-b3c1-2e91c08daa07', '1b385172-3928-4691-9759-d51d2a02b0bf'),

    -- PROFILES OF EVENT ORGANIZERS
    ('3d82e9b8-3d9b-4c7d-b244-1e6725b78456', false, 'jane.smith@example.com', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','6e569715-d341-4431-8370-d7516907d2e2', '1b385172-3928-4691-9759-d51d2a02b0bf'),
    ('91c1e927-9f79-44f4-b12f-bcbf16b16c6f', true, 'mark.jones@example.com', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','6e569715-d341-4431-8370-d7516907d2e2', '1b385172-3928-4691-9759-d51d2a02b0bf'),
    ('a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e', false, 'susan.brown@example.com', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','6e569715-d341-4431-8370-d7516907d2e2', '1b385172-3928-4691-9759-d51d2a02b0bf'),
    ('0cd13f4e-f7de-4533-9071-c42b7b3b4d45', true, 'tom.williams@example.com', true, true, '1233','6e569715-d341-4431-8370-d7516907d2e2', '1b385172-3928-4691-9759-d51d2a02b0bf'),
    ('97910c30-9cc7-4944-89cc-9397c98e0f78', true, 'o', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','6e569715-d341-4431-8370-d7516907d2e2', '1b385172-3928-4691-9759-d51d2a02b0bf'),

    -- PROFILES OF SELLERS
    ('e852c4ff-3d2b-47ea-b1fd-e711cf18b1d7', false, 'emily.davis@example.com', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','4e3484cf-382f-448d-a1db-1c1063ab41ee', '1b385172-3928-4691-9759-d51d2a02b0bf'),
    ('fddde66f-9b84-4bb8-b408-eae5b815ae69', true, 'robert.martin@example.com', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','4e3484cf-382f-448d-a1db-1c1063ab41ee', '1b385172-3928-4691-9759-d51d2a02b0bf'),
    ('db48e7ac-1d35-4d9d-8e09-bf2e86533b91', false, 'lily.martinez@example.com', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','4e3484cf-382f-448d-a1db-1c1063ab41ee', '1b385172-3928-4691-9759-d51d2a02b0bf'),
    ('8bc76c7b-fc9d-469b-a7b1-2d2291d9a9b6', true, 'samuel.jackson@example.com', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','4e3484cf-382f-448d-a1db-1c1063ab41ee', '1b385172-3928-4691-9759-d51d2a02b0bf'),
    ('de186d6f-9799-4ab0-a726-5563f51d9889', true, 's', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','4e3484cf-382f-448d-a1db-1c1063ab41ee', '1b385172-3928-4691-9759-d51d2a02b0bf'),

    -- PROFILES OF GUESTS
    ('9a4531e5-2fda-42bc-8355-d7991bfc8ff4', true, 'john.smith@example.com', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','9150703f-0449-40e1-b1fb-f0cb0a31b7b0', '1b385172-3928-4691-9759-d51d2a02b0bf'),
    ('0d1f5f7a-6fcf-42d9-82fe-0090a35ea88c', false, 'grace.perez@example.com', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','9150703f-0449-40e1-b1fb-f0cb0a31b7b0', '1b385172-3928-4691-9759-d51d2a02b0bf'),
    ('679eb520-7b8d-4c3a-b99f-720e6cfb759b', true, 'oliver.harris@example.com', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','9150703f-0449-40e1-b1fb-f0cb0a31b7b0', '1b385172-3928-4691-9759-d51d2a02b0bf'),
    ('4d7248cb-d5f2-4e9b-9eb2-b8ad1de05bcf', false, 'mia.rodriguez@example.com', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','9150703f-0449-40e1-b1fb-f0cb0a31b7b0', '1b385172-3928-4691-9759-d51d2a02b0bf'),
    ('27e8a1b2-4d23-4b6f-b5a5-79a76d6b758e', true, 'jason.wilson@example.com', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','9150703f-0449-40e1-b1fb-f0cb0a31b7b0', '1b385172-3928-4691-9759-d51d2a02b0bf'),
    ('542c3a1b-ffea-421d-b967-7d45968c6506', false, 'sophia.morris@example.com', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','9150703f-0449-40e1-b1fb-f0cb0a31b7b0', '1b385172-3928-4691-9759-d51d2a02b0bf'),
    ('05fdc5be-d59b-468e-8466-d951a4a8d457', true, 'daniel.clark@example.com', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','9150703f-0449-40e1-b1fb-f0cb0a31b7b0', '1b385172-3928-4691-9759-d51d2a02b0bf'),
    ('03b88b74-0797-4f35-b15f-ff2a3c3e5c88', false, 'isabella.wright@example.com', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','9150703f-0449-40e1-b1fb-f0cb0a31b7b0', '1b385172-3928-4691-9759-d51d2a02b0bf'),
    ('70c7425e-c5f0-44f0-9e9d-44e4087fce62', true, 'ethan.king@example.com', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','9150703f-0449-40e1-b1fb-f0cb0a31b7b0', '1b385172-3928-4691-9759-d51d2a02b0bf'),
    ('5a72482a-cd36-46e4-8c09-7b2ff1e22071', false, 'madison.green@example.com', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','9150703f-0449-40e1-b1fb-f0cb0a31b7b0', '1b385172-3928-4691-9759-d51d2a02b0bf'),
    ('3cfb1016-0d0e-42f8-924c-324ba2249ded', false, 'g', true, true, '{bcrypt}$2a$10$JO/kNwG1jWv9zuiP5pTkoeH270HGlaMothI4SRpc1n8t6nVScuL5K','9150703f-0449-40e1-b1fb-f0cb0a31b7b0', '1b385172-3928-4691-9759-d51d2a02b0bf');

INSERT INTO profile_blocked_users (profile_id, blocked_user_id)
VALUES
    -- EVENT ORGANIZER ON SELLER
    ('3d82e9b8-3d9b-4c7d-b244-1e6725b78456', 'e852c4ff-3d2b-47ea-b1fd-e711cf18b1d7'),

    -- SELLER ON EVENT ORGANIZER
    ('fddde66f-9b84-4bb8-b408-eae5b815ae69', '91c1e927-9f79-44f4-b12f-bcbf16b16c6f');

-- NO SENSE TO ADD MORE BLOCKS BECAUSE OTHER FUNCTIONALITY WILL BE BLOCKED

-- USERS

INSERT INTO admin (id, profile_id) VALUES
                                       ('3570d6ff-6472-42a5-90fc-9b143db9f778', '7a92c4d7-8742-4a25-aee8-750ca987b42e'),
                                       ('5a5a94d3-443c-4eda-a263-c541647d7b7c', 'b2d2e1f0-06ae-4b0f-9cd0-7b5b31b1e9fe'),
                                       ('4f18aa9e-d378-484b-8e90-d6c05c178495', 'f591021a-5b30-491f-a87f-02d4bec6d508');

INSERT INTO event_organizer (id, profile_id, address, city, name, surname, telephone_number)
VALUES
    ('b38d716b-4d2a-4fd3-b18c-bfa128f24b99', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456', '123 Main St, Suite 5', 'Novi Sad', 'Jane', 'Smith', '+1 212-555-1234'),
    ('47c5fa7c-0d12-48e2-a4ed-9e4f441b383f', '91c1e927-9f79-44f4-b12f-bcbf16b16c6f', '456 Oak Ave', 'Niš', 'Mark', 'Jones', '+1 323-555-5678'),
    ('9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e', '789 Pine Rd', 'Novi Sad', 'Susan', 'Brown', '+1 312-555-9101'),
    ('1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236', '0cd13f4e-f7de-4533-9071-c42b7b3b4d45', '321 Elm Blvd', 'Niš', 'Tom', 'Williams', '+1 415-555-1122'),
    ('1109388b-c4ad-4ce2-b99e-57890d8e8ddd', '97910c30-9cc7-4944-89cc-9397c98e0f78', '321 Elm Blvd', 'Niš', 'Tom', 'Williams', '+1 415-555-1122');

INSERT INTO seller (id, profile_id, address, city, name, surname, telephone_number, description)
VALUES
    ('2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5', 'e852c4ff-3d2b-47ea-b1fd-e711cf18b1d7', '987 Maple Dr', 'Niš', 'Emily', 'Davis', '+1 206-555-3141', 'Specializes in organizing seamless events, from weddings to corporate functions, offering full-service planning, coordination, and vendor management.'),
    ('a1d764df-9b5c-4f62-b0a1-13d8edfcf4a3', 'fddde66f-9b84-4bb8-b408-eae5b815ae69', '654 Birch Blvd', 'Novi Sad', 'Robert', 'Martin', '+1 305-555-7282', 'Provides personalized event decorations, including floral arrangements, backdrops, and themed designs, creating memorable settings for every occasion.'),
    ('6e0c99f4-226f-49fb-bc4b-1f59ff671b95', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91', '321 Cedar Ln', 'Novi Sad', 'Lily', 'Martinez', '+1 617-555-9865', 'Delivers high-quality catering with customized menus, including dietary options, to elevate the culinary experience at your event.'),
    ('c5e2a004-83b0-4f91-9ff2-c235f2166b72', '8bc76c7b-fc9d-469b-a7b1-2d2291d9a9b6', '543 Pinehill St', 'Niš', 'Samuel', 'Jackson', '+1 512-555-3746', 'Offers top-notch sound, lighting, and projection equipment for events, ensuring optimal performance for conferences, weddings, and live performances.'),
    ('f70321a0-5620-44e5-a92f-53427522562c', 'de186d6f-9799-4ab0-a726-5563f51d9889', '543 Pinehill St', 'Niš', 'Samuel', 'Jackson', '+1 512-555-3746', 'Offers top-notch sound, lighting, and projection equipment for events, ensuring optimal performance for conferences, weddings, and live performances.');

INSERT INTO guest (id, profile_id, name, surname)
VALUES
    ('634182f1-9a18-433b-82d8-dad5aa4069f8', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4', 'John', 'Smith'),
    ('c633e080-fad0-4195-8a52-688c149700a1', '0d1f5f7a-6fcf-42d9-82fe-0090a35ea88c', 'Grace', 'Perez'),
    ('95a4669b-9ee6-4608-a4d1-ae52da25be36', '679eb520-7b8d-4c3a-b99f-720e6cfb759b', 'Oliver', 'Harris'),
    ('0f0e83c6-6764-4c27-bca6-7369aea6acaa', '4d7248cb-d5f2-4e9b-9eb2-b8ad1de05bcf', 'Mia', 'Rodriguez'),
    ('4b423147-32cf-4a90-9238-a3a5934aaee9', '27e8a1b2-4d23-4b6f-b5a5-79a76d6b758e', 'Jason', 'Wilson'),
    ('97aaf376-4054-417d-8caf-ecbfecb185c3', '542c3a1b-ffea-421d-b967-7d45968c6506', 'Sophia', 'Morris'),
    ('f4618423-15ac-4772-96ac-7144e240b584', '05fdc5be-d59b-468e-8466-d951a4a8d457', 'Daniel', 'Clark'),
    ('70e77225-b0a3-403c-b900-0f1788c93780', '03b88b74-0797-4f35-b15f-ff2a3c3e5c88', 'Isabella', 'Wright'),
    ('d8413682-21f1-4e35-ba8d-276334c9ffab', '70c7425e-c5f0-44f0-9e9d-44e4087fce62', 'Ethan', 'King'),
    ('e078765f-21ee-49c0-90a4-1377fe1386e5', '5a72482a-cd36-46e4-8c09-7b2ff1e22071', 'Madison', 'Green'),
    ('60fb3470-137c-4761-9da4-9aa014daa42f', '3cfb1016-0d0e-42f8-924c-324ba2249ded', 'Madison', 'Green');

-- PRODUCTS

INSERT INTO product_category (id, description, is_deleted, is_pending, name)
VALUES
    ('e5ad2f36-0d76-43dc-9645-531381c5d29c', '', false, true, 'Associated category 1'),
    ('28db7e87-9d76-4ba7-be6e-1bb91e30228b', '', false, true, 'Pending category 2'),
    ('befeb37b-49b3-4a3f-8055-731db9fbfef4', '', false, true, 'Pending category 3'),
    ('fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b', 'Fireworks and pyrotechnic displays for events and celebrations.', false, false, 'Fireworks'),
    ('d13a78bc-9256-4e7f-90b5-354e3f7ab5db', 'Variety of alcoholic and non-alcoholic drinks for events and gatherings.', false, false, 'Drinks'),
    ('ae2a31d5-c7d1-4c30-b268-82d129edb3f6', 'Food and snack options for event guests and parties.', false, false, 'Food'),
    ('f3b6fdeb-c684-47ac-b0be-b4173f36d3b7', 'Event decorations including flowers, table settings, and themes.', false, false, 'Decorations'),
    ('1e3939e7-6822-432a-b322-0ab107f8d582', 'Lighting solutions for events, concerts, and outdoor gatherings.', false, false, 'Lighting'),
    ('72ecfc89-6745-4f5b-a7f2-97fd9ad3f890', 'Stage and tent rentals for outdoor and indoor events.', false, false, 'Stage & Tents');

INSERT INTO static_product (static_product_id, product_category_id, pending, seller_id)
VALUES
    -- FIREWORKS CATEGORY
    ('5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('b19a7c35-3d60-4fe2-a8b2-e8a2c2741b3b', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('ca97b729-3035-4170-b5f7-83f1e63e9b8a', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b', false, 'a1d764df-9b5c-4f62-b0a1-13d8edfcf4a3'),
    ('cefe62ba-b263-4db8-8f4f-3b93ebff25cf', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('9271b6be-64bc-4cfe-bf57-dfa4a0cdad44', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),

    ('8e862226-257d-473c-94eb-aedff374dedf', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('fdf4285e-1619-4f13-819a-0dc6843c4ce1', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('11460238-d909-4b1e-ba1b-651904b36eb0', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),

    -- DRINKS CATEGORY
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db', false, 'a1d764df-9b5c-4f62-b0a1-13d8edfcf4a3'),
    ('04fe2f92-7732-43c9-b9d6-2d801f47b0e0', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('7b95be87-e4fd-4a04-b58d-8297c99a4cd1', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),
    ('ee3c6987-dfd9-4375-930f-cf14c55126a7', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('b2f0b7fe-803a-4219-8663-5c93c63e3073', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('462f2b19-45ff-4d91-92bc-8f95cfb78f88', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db', false, 'a1d764df-9b5c-4f62-b0a1-13d8edfcf4a3'),
    ('ab9f56c0-4379-4b8c-9673-5355b4de07ca', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('e8f5c5fa-3963-4f1a-bb0f-bcd9e803625a', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),
    ('f6988288-c4d6-4b3a-bae4-1b6b81a4b02f', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('24a7cf0a-8a8b-46ad-bb23-f4c8be7b154d', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db', false, 'a1d764df-9b5c-4f62-b0a1-13d8edfcf4a3'),
    ('b94869d7-c5cf-4ff2-8b9a-2d26cd722ba7', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('1c74e529-d8fc-4fbd-b99a-09b1229b4f59', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),
    ('3c2ef35b-6e5d-429b-9bbf-62c9b7a4ab3b', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('9d463d25-660a-4202-81e4-9d4ea4e64ab9', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db', false, 'a1d764df-9b5c-4f62-b0a1-13d8edfcf4a3'),
    ('5ff14d28-e07b-4f98-a63e-1397243a15b0', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),

    -- FOOD CATEGORY
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('11a3de8c-7a9b-43d7-bbc0-b5d3a62fe59e', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),
    ('ef58edcf-2fe7-4595-bdf6-9ef029ff4a98', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('327b9456-b9da-42e1-86c3-f4b25f38a0e9', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('ae8fd23a-1347-4f16-9295-d3cba2721db1', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),
    ('15d6c5fa-f00e-41e3-8cc2-84b05ed9bcb5', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('72dbf31f-e7bb-43d5-890d-bde5c83ef03e', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('cb64a27b-28d0-4b99-8241-cedfe014a635', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6', false, 'a1d764df-9b5c-4f62-b0a1-13d8edfcf4a3'),
    ('b42d2a1a-b118-49ea-b83a-b1aee1b67e2e', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('a39fc8ad-becf-4d18-b79e-074fa9d6201a', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),
    ('6f2fd5b4-69de-4c95-92f2-02d67d81c204', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('d57c1395-694b-4e69-8f01-f6a5b062502b', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('74aee64e-e8e5-490a-90c0-991d76c76752', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6', false, 'a1d764df-9b5c-4f62-b0a1-13d8edfcf4a3'),
    ('7ffdb1b9-36a7-4d5c-a1d4-d37a4cfa9282', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('9ea67f4b-50fd-4b4b-b0cc-693e35d5dbb5', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),

    -- DECORATION CATEGORY
    ('a3f5b299-1bc5-45ec-bb6e-b564e0d11c94', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('bf00b4b0-b3d9-4660-87c5-97f2b4159be0', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('c78d7030-5204-4734-b92f-62c9e7040b33', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7', false, 'a1d764df-9b5c-4f62-b0a1-13d8edfcf4a3'),
    ('36d5e0a5-e3ea-4604-bf3c-467d62b35ec6', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('1b74c3b4-12fc-48ea-b7b3-55600d1f5c78', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),

    -- LIGHTNING CATEGORY
    ('13b86db3-3e28-4f01-bb8b-97d9d0a7d5c1', '1e3939e7-6822-432a-b322-0ab107f8d582', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('3c4a95b6-f13e-4a1a-b37e-dba49f72c9f6', '1e3939e7-6822-432a-b322-0ab107f8d582', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('6a9cd799-4de1-4d3f-a70e-70f5157029b3', '1e3939e7-6822-432a-b322-0ab107f8d582', false, 'a1d764df-9b5c-4f62-b0a1-13d8edfcf4a3'),
    ('8c829577-d7d1-4ea5-b983-e21a41307947', '1e3939e7-6822-432a-b322-0ab107f8d582', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('7b5912f7-d29a-4f60-a9c9-9e8d8acbadae', '1e3939e7-6822-432a-b322-0ab107f8d582', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),

    -- STAGE & TENTS CATEGORY
    ('7e9d4ad7-b82c-43b5-9a4c-9e343f038779', '72ecfc89-6745-4f5b-a7f2-97fd9ad3f890', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('e1c82607-b99a-41c4-8cd0-e09e4cc9f93d', '72ecfc89-6745-4f5b-a7f2-97fd9ad3f890', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('ac53c13a-d9cf-46b4-bdf9-3f9a4477e1ed', '72ecfc89-6745-4f5b-a7f2-97fd9ad3f890', false, 'a1d764df-9b5c-4f62-b0a1-13d8edfcf4a3'),
    ('9f0d0870-1f5c-4067-b2f5-fb22a2fe03e3', '72ecfc89-6745-4f5b-a7f2-97fd9ad3f890', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('c92a9ef9-c5e6-4ac5-b990-1c499f7a9cc3', '72ecfc89-6745-4f5b-a7f2-97fd9ad3f890', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),

    -- PENDING PRODUCT CATEGORY 1
    ('8abe1d3f-3815-4b7f-a9c4-1ea9a86a703c', 'e5ad2f36-0d76-43dc-9645-531381c5d29c', true, 'a1d764df-9b5c-4f62-b0a1-13d8edfcf4a3');

INSERT INTO versioned_product (static_product_id, version, is_active, is_available, is_last_version, is_private, name, price, sale_percentage, description)
VALUES
    -- FIREWORKS CATEGORY
    ('5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, true, true, true,  false, 'Fountain Fireworks', 130.00, 0.1, 'A spectacular display of sparking fireworks that create a fountain of lights and colors.'),
    ('b19a7c35-3d60-4fe2-a8b2-e8a2c2741b3b', 1, true, true,  true, false, 'Roman Candles', 200.00, 0.2, 'Traditional fireworks that shoot colorful stars into the sky with a crackling sound.'),
    ('ca97b729-3035-4170-b5f7-83f1e63e9b8a', 1, false, true, true,  true, 'Firework Shells', 120.00, 0.15, 'Large shells that explode into a colorful burst in the sky, perfect for grand displays.'),
    ('cefe62ba-b263-4db8-8f4f-3b93ebff25cf', 1, true, false, true,  false, 'Sparklers', 50.00, 0.05, 'Small handheld fireworks that emit sparkling lights, ideal for kids and celebrations.'),
    ('9271b6be-64bc-4cfe-bf57-dfa4a0cdad44', 1, false, true,  true, false, 'Confetti Cannons', 77.00, 0.0, 'Cannons that shoot colorful confetti into the air, perfect for celebrations and events.'),

    ('8e862226-257d-473c-94eb-aedff374dedf', 1, false, true, true,  false, 'not active', 130.00, 0.1, 'desc.'),
    ('fdf4285e-1619-4f13-819a-0dc6843c4ce1', 1, true, true, true,  true, 'not public', 130.00, 0.1, 'desc.'),
    ('11460238-d909-4b1e-ba1b-651904b36eb0', 1, true, false, true,  false, 'not available', 130.00, 0.1, 'desc.'),

    -- DRINKS CATEGORY
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, true, true,  true, false, 'Champagne', 930.00, 0.0, 'A premium sparkling wine to celebrate special moments with style.'),
    ('04fe2f92-7732-43c9-b9d6-2d801f47b0e0', 1, true, true,  true, false, 'Cocktail Mixes', 150.00, 0.15, 'Variety of cocktail mixes perfect for creating delicious drinks for your event.'),
    ('7b95be87-e4fd-4a04-b58d-8297c99a4cd1', 1, true, true,  true, false, 'Bottled Water', 100.00, 0.0, 'Pure and refreshing bottled water to keep your guests hydrated.'),
    ('ee3c6987-dfd9-4375-930f-cf14c55126a7', 1, true, true,  true, false, 'Fruit Punch', 200.00, 0.1, 'A sweet and fruity non-alcoholic drink, perfect for all ages.'),
    ('b2f0b7fe-803a-4219-8663-5c93c63e3073', 1, true, false, true,  false, 'Beer Kegs', 800.00, 0.3, 'Large kegs of beer to serve at your event, ideal for larger gatherings.'),
    ('462f2b19-45ff-4d91-92bc-8f95cfb78f88', 1, true, true,  true, false, 'Premium Whiskey', 950.00, 0.1, 'A smooth and refined whiskey, perfect for sipping at upscale events.'),
    ('ab9f56c0-4379-4b8c-9673-5355b4de07ca', 1, true, true,  true, false, 'Margarita Cocktail Mix', 150.00, 0.15, 'A refreshing mix for making the perfect margaritas at your event.'),
    ('e8f5c5fa-3963-4f1a-bb0f-bcd9e803625a', 1, true, true,  true, false, 'Red Wine (Cabernet Sauvignon)', 500.00, 0.2, 'A full-bodied red wine with rich flavor, perfect for fine dining events.'),
    ('f6988288-c4d6-4b3a-bae4-1b6b81a4b02f', 1, false, true, true,  false, 'Non-Alcoholic Beer', 200.00, 0.05, 'A refreshing alternative to beer without the alcohol content.'),
    ('24a7cf0a-8a8b-46ad-bb23-f4c8be7b154d', 1, true, true,  true, true, 'Iced Tea with Lemon', 100.00, 0.0, 'A chilled and refreshing iced tea, served with a slice of lemon for extra zest.'),
    ('b94869d7-c5cf-4ff2-8b9a-2d26cd722ba7', 1, true, true,  true, false, 'Sparkling Water', 75.00, 0.1, 'Bubbly sparkling water, a refreshing choice for any occasion.'),
    ('1c74e529-d8fc-4fbd-b99a-09b1229b4f59', 1, true, true,  true, false, 'Frozen Daiquiri Mix', 180.00, 0.05, 'A frozen mix to create the perfect daiquiri, perfect for summer events.'),
    ('3c2ef35b-6e5d-429b-9bbf-62c9b7a4ab3b', 1, true, true,  true, false, 'Prosecco', 300.00, 0.1, 'A light and bubbly sparkling wine, ideal for toasts and celebrations.'),
    ('9d463d25-660a-4202-81e4-9d4ea4e64ab9', 1, true, true,  true, false, 'Lemonade (Large Batch)', 150.00, 0.0, 'Freshly squeezed lemonade, perfect for serving in large quantities at events.'),
    ('5ff14d28-e07b-4f98-a63e-1397243a15b0', 1, true, true,  true, false, 'Classic Mojito Cocktail Mix', 250.00, 0.15, 'A mix for preparing the classic mojito, a refreshing minty cocktail.'),

    -- FOOD CATEGORY
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, true, true,  true, false, 'Finger Foods & Canapés', 250.00, 0.0, 'Small, bite-sized snacks and appetizers perfect for mingling guests.'),
    ('11a3de8c-7a9b-43d7-bbc0-b5d3a62fe59e', 1, true, false, true,  false, 'Gourmet Sandwich Platters', 350.00, 0.1, 'A variety of gourmet sandwiches, perfect for buffets or gatherings.'),
    ('ef58edcf-2fe7-4595-bdf6-9ef029ff4a98', 1, true, true,  true, false, 'BBQ Buffet', 180.00, 0.15, 'A selection of grilled meats, sides, and sauces, perfect for outdoor events.'),
    ('327b9456-b9da-42e1-86c3-f4b25f38a0e9', 1, true, true,  true, false, 'Chocolate Fountain', 900.00, 0.2, 'A decadent chocolate fountain with fresh fruits and marshmallows for dipping.'),
    ('ae8fd23a-1347-4f16-9295-d3cba2721db1', 1, true, true,  true, false, 'Mini Burgers', 550.00, 0.0, 'Small, delicious burgers perfect for events with a casual vibe.'),
    ('15d6c5fa-f00e-41e3-8cc2-84b05ed9bcb5', 1, true, true,  true, true, 'Gourmet Pizza Buffet', 950.00, 0.1, 'A variety of gourmet pizzas, perfect for casual gatherings or buffets.'),
    ('72dbf31f-e7bb-43d5-890d-bde5c83ef03e', 1, true, true,  true, false, 'Sushi Rolls & Nigiri Platter', 120.00, 0.15, 'A selection of fresh sushi rolls and nigiri, ideal for upscale events.'),
    ('cb64a27b-28d0-4b99-8241-cedfe014a635', 1, true, true,  true, false, 'Grilled Meat Skewers', 650.00, 0.1, 'Tender, grilled skewers of meat, perfect for serving at barbecues and outdoor events.'),
    ('b42d2a1a-b118-49ea-b83a-b1aee1b67e2e', 1, true, false, true,  false, 'Fresh Seafood Platter', 130.00, 0.2, 'A selection of fresh seafood, perfect for luxury events or seafood lovers.'),
    ('a39fc8ad-becf-4d18-b79e-074fa9d6201a', 1, true, true,  true, false, 'Vegetarian Tacos', 400.00, 0.05, 'Flavorful vegetarian tacos filled with fresh ingredients, ideal for plant-based diets.'),
    ('6f2fd5b4-69de-4c95-92f2-02d67d81c204', 1, false, true, true,  false, 'Mini Quiche Selection', 250.00, 0.1, 'A selection of mini quiches, perfect for brunches or light bites at events.'),
    ('d57c1395-694b-4e69-8f01-f6a5b062502b', 1, true, true,  true, false, 'Gourmet Sandwiches Platter', 500.00, 0.1, 'A selection of gourmet sandwiches with fresh ingredients for a delightful meal.'),
    ('74aee64e-e8e5-490a-90c0-991d76c76752', 1, true, true,  true, false, 'Assorted Pasta Salad', 350.00, 0.15, 'A variety of fresh pasta salad options, perfect as a side dish for any gathering.'),
    ('7ffdb1b9-36a7-4d5c-a1d4-d37a4cfa9282', 1, true, true,  true, false, 'Charcuterie Board', 850.00, 0.2, 'An assortment of cured meats, cheeses, and accompaniments for a sophisticated snack.'),
    ('9ea67f4b-50fd-4b4b-b0cc-693e35d5dbb5', 1, true, true,  true, true, 'Dessert Buffet', 150.00, 0.25, 'A delightful variety of sweet treats, perfect for ending a meal on a high note.'),

    -- DECORATION CATEGORY
    ('a3f5b299-1bc5-45ec-bb6e-b564e0d11c94', 1, true, true,  true, false, 'Flower Arrangements', 250.00, 0.05, 'Beautiful floral arrangements to add elegance to any event.'),
    ('bf00b4b0-b3d9-4660-87c5-97f2b4159be0', 1, true, true,  true, true, 'Table Centerpieces', 180.00, 0.1, 'Elegant centerpieces to decorate tables and set the tone for the event.'),
    ('c78d7030-5204-4734-b92f-62c9e7040b33', 1, true, true,  true, false, 'Event Balloons', 75.00, 0.2, 'Colorful balloons to add a fun and festive touch to your event.'),
    ('36d5e0a5-e3ea-4604-bf3c-467d62b35ec6', 1, false, true, true,  false, 'Backdrop Curtains', 400.00, 0.1, 'Elegant curtains for creating a backdrop or dividing event spaces.'),
    ('1b74c3b4-12fc-48ea-b7b3-55600d1f5c78', 1, true, true,  true, false, 'Event Banners', 150.00, 0.05, 'Customizable banners to promote your event or set the theme.'),

    -- LIGHTNING CATEGORY
    ('13b86db3-3e28-4f01-bb8b-97d9d0a7d5c1', 1, true, true,  true, false, 'Spotlight Rental', 500.00, 0.1, 'Rental service for high-intensity spotlights to highlight special moments.'),
    ('3c4a95b6-f13e-4a1a-b37e-dba49f72c9f6', 1, true, false, true,  false, 'Fairy Lights', 200.00, 0.15, 'Delicate fairy lights for creating a magical atmosphere at your event.'),
    ('6a9cd799-4de1-4d3f-a70e-70f5157029b3', 1, true, true,  true, false, 'LED Wall Washers', 120.00, 0.2, 'LED lights that wash walls with vibrant colors, perfect for large venues or stages.'),
    ('8c829577-d7d1-4ea5-b983-e21a41307947', 1, true, true,  true, false, 'Outdoor Flood Lights', 350.00, 0.0, 'Powerful floodlights for illuminating outdoor spaces during events.'),
    ('7b5912f7-d29a-4f60-a9c9-9e8d8acbadae', 1, true, true,  true, false, 'String Lights for Tents', 180.00, 0.01, 'String lights designed for tents, creating a warm and inviting ambiance.'),

    -- STAGE & TENTS CATEGORY
    ('7e9d4ad7-b82c-43b5-9a4c-9e343f038779', 1, false, true, true,  false, 'Stage Setup (Small)', 150.00, 0.1, 'Compact stage setup, perfect for smaller performances or presentations.'),
    ('e1c82607-b99a-41c4-8cd0-e09e4cc9f93d', 1, true, true, true, true, 'Outdoor Canopy Tent', 120.00, 0.05, 'A spacious and durable outdoor canopy tent, perfect for outdoor events and gatherings.'),
    ('ac53c13a-d9cf-46b4-bdf9-3f9a4477e1ed', 1, true, true, true, false, 'Portable Stage for Events', 220.00, 0.1, 'A compact, portable stage setup ideal for events that require mobility and ease of installation.'),
    ('9f0d0870-1f5c-4067-b2f5-fb22a2fe03e3', 1, true, true, true, false, 'Event Tent (Large)', 300.00, 0.2, 'A large event tent that offers ample space for conferences, parties, and festivals.'),
    ('c92a9ef9-c5e6-4ac5-b990-1c499f7a9cc3', 1, true, true, true, false, 'Stage Lights & Effects', 600.00, 0.0, 'Dynamic stage lighting and effects to enhance the atmosphere and highlight performances.'),

    -- PENDING PRODUCT CATEGORY 1
    ('8abe1d3f-3815-4b7f-a9c4-1ea9a86a703c', 1, true, true, true, false, 'Pending Product 1', 600.00, 0.0, 'A product that is currently under review and pending approval for the catalog.');

INSERT INTO public.versioned_product_images(
    versioned_product_static_product_id, versioned_product_version, images)
VALUES
    ('5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, '36b3043d-4541-4704-bc2c-6ea8522ec3f0'),
    ('5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, '57acb641-4f00-40b6-9f8f-f9144a802172'),
    ('5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, '913a4bcb-c52e-4587-ad7b-7a459463ecf8'),
    ('5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, '3fd83482-5c0c-46ea-b5c3-1c7a0a2b942d'),
    ('b19a7c35-3d60-4fe2-a8b2-e8a2c2741b3b', 1, 'ab9d05c3-c093-41e6-a7db-f3807ccae913'),
    ('b19a7c35-3d60-4fe2-a8b2-e8a2c2741b3b', 1, '4e9818b0-a341-4f8f-8aa8-eee496bf43a8'),
    ('b19a7c35-3d60-4fe2-a8b2-e8a2c2741b3b', 1, '5d237ba3-a79c-4578-aecd-c9163d8efc96'),
    ('ca97b729-3035-4170-b5f7-83f1e63e9b8a', 1, '4953eb42-df50-40fd-9fc3-40934433e17d'),
    ('ca97b729-3035-4170-b5f7-83f1e63e9b8a', 1, '6264129f-0e86-4807-b666-3a717f8641c2'),
    ('ca97b729-3035-4170-b5f7-83f1e63e9b8a', 1, 'f286c201-f614-493d-aa48-3069fe52797d'),
    ('ca97b729-3035-4170-b5f7-83f1e63e9b8a', 1, 'dd122126-293a-48b2-af0b-2050843772df'),
    ('cefe62ba-b263-4db8-8f4f-3b93ebff25cf', 1, 'ae16ea61-0020-4ef0-afbd-2e5dde43de3c'),
    ('cefe62ba-b263-4db8-8f4f-3b93ebff25cf', 1, '24360d16-4327-41d3-ab97-e5f5d356a791'),
    ('cefe62ba-b263-4db8-8f4f-3b93ebff25cf', 1, 'd32e7024-e976-421a-afd9-2bb99d984255'),
    ('cefe62ba-b263-4db8-8f4f-3b93ebff25cf', 1, '8027f4de-f6ab-4574-8dae-23c695e0c9a1'),
    ('9271b6be-64bc-4cfe-bf57-dfa4a0cdad44', 1, '532ac5ce-a8fb-4593-8087-73cd91076cc0'),
    ('9271b6be-64bc-4cfe-bf57-dfa4a0cdad44', 1, '3775badf-2cc0-4100-8bfd-2bae1a991531'),
    ('9271b6be-64bc-4cfe-bf57-dfa4a0cdad44', 1, '8f6580df-f8fc-42b1-9723-492a486da6f8'),
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, '535ce85f-d7f5-4d3e-be47-89d2c02a7914'),
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, '0b364f67-3a50-4669-b0ce-e6395564c889'),
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, '09651ecc-3f1c-41fd-9c94-22ce2b7a2b90'),
    ('04fe2f92-7732-43c9-b9d6-2d801f47b0e0', 1, '29e8085c-b582-4799-b39f-38388364ff81'),
    ('04fe2f92-7732-43c9-b9d6-2d801f47b0e0', 1, 'e2bdd9d1-2269-4108-b446-b4aebb44a849'),
    ('7b95be87-e4fd-4a04-b58d-8297c99a4cd1', 1, 'e90379da-ed1f-4d76-8655-ddf8a4b9b575'),
    ('7b95be87-e4fd-4a04-b58d-8297c99a4cd1', 1, '007b7ab1-1024-436b-afc9-a9f6f3547d12'),
    ('7b95be87-e4fd-4a04-b58d-8297c99a4cd1', 1, '9fd29772-09df-456b-b447-36803a6871c1'),
    ('ee3c6987-dfd9-4375-930f-cf14c55126a7', 1, 'ef561db8-e9d8-4d1a-8c0f-476cf382fef0'),
    ('ee3c6987-dfd9-4375-930f-cf14c55126a7', 1, '8d13032c-dd8d-4f64-b8cc-970d93c504be'),
    ('b2f0b7fe-803a-4219-8663-5c93c63e3073', 1, 'ae271221-0d72-471a-8cfd-9d18be19e709'),
    ('b2f0b7fe-803a-4219-8663-5c93c63e3073', 1, 'abd6d5ee-9eaf-4476-8993-14cf4c5ae8e4'),
    ('b2f0b7fe-803a-4219-8663-5c93c63e3073', 1, '94a3c254-0e86-4f6e-8125-585c55b6afd4'),
    ('462f2b19-45ff-4d91-92bc-8f95cfb78f88', 1, 'd99e4080-a302-4f91-9f96-c1edf891aa8f'),
    ('462f2b19-45ff-4d91-92bc-8f95cfb78f88', 1, 'c3b12870-7bb9-433c-93fb-dfb6a062394d'),
    ('ab9f56c0-4379-4b8c-9673-5355b4de07ca', 1, '42c90f9d-c253-4824-8321-90c671f14ab0'),
    ('ab9f56c0-4379-4b8c-9673-5355b4de07ca', 1, 'e9ee6e63-5521-4601-9af8-d0f0eb446eb7'),
    ('e8f5c5fa-3963-4f1a-bb0f-bcd9e803625a', 1, '572294b2-43fc-4d68-b723-3550badb0272'),
    ('e8f5c5fa-3963-4f1a-bb0f-bcd9e803625a', 1, '3a02f0dd-7ec0-4597-a257-ceb3062f2f55'),
    ('f6988288-c4d6-4b3a-bae4-1b6b81a4b02f', 1, '2262290a-2034-4a69-ae06-2175371b767d'),
    ('f6988288-c4d6-4b3a-bae4-1b6b81a4b02f', 1, '0ee57185-6823-4669-a445-30f0033fc121'),
    ('24a7cf0a-8a8b-46ad-bb23-f4c8be7b154d', 1, '972e42b3-5171-4794-9f50-95fabcafb263'),
    ('24a7cf0a-8a8b-46ad-bb23-f4c8be7b154d', 1, 'f1ef0653-fb6a-417c-ba04-fe00f0ace843'),
    ('24a7cf0a-8a8b-46ad-bb23-f4c8be7b154d', 1, '355a684f-734e-4e39-9f9b-dcb3da8616b3'),
    ('24a7cf0a-8a8b-46ad-bb23-f4c8be7b154d', 1, '4d0e0ae4-2f16-4a8a-9f7d-7d8492d68cc8'),
    ('b94869d7-c5cf-4ff2-8b9a-2d26cd722ba7', 1, '08ecba58-840c-474f-9708-dc0fc92efb25'),
    ('b94869d7-c5cf-4ff2-8b9a-2d26cd722ba7', 1, 'fb436a26-5941-425f-bf1a-e4d132cce361'),
    ('1c74e529-d8fc-4fbd-b99a-09b1229b4f59', 1, 'f7b8ee81-532e-4e8d-824e-52a7cb564dab'),
    ('1c74e529-d8fc-4fbd-b99a-09b1229b4f59', 1, '8d5a9b3d-4788-4a1b-95fa-0ae94cd39773'),
    ('1c74e529-d8fc-4fbd-b99a-09b1229b4f59', 1, '6abeafc6-b92a-4bdb-aa98-96224cc9b5a6'),
    ('1c74e529-d8fc-4fbd-b99a-09b1229b4f59', 1, '0680b7ab-93e8-4758-998f-c25900e87272'),
    ('3c2ef35b-6e5d-429b-9bbf-62c9b7a4ab3b', 1, '145e3d03-1331-4e5a-9e4d-e2999d836a93'),
    ('3c2ef35b-6e5d-429b-9bbf-62c9b7a4ab3b', 1, 'df523c1d-b939-4e68-940c-5cc07a634aa7'),
    ('3c2ef35b-6e5d-429b-9bbf-62c9b7a4ab3b', 1, '8c6c3620-feb1-42d7-b5ef-6f67a931b092'),
    ('3c2ef35b-6e5d-429b-9bbf-62c9b7a4ab3b', 1, 'd7e1784d-d2b6-45d8-8fe5-13d3f486900c'),
    ('9d463d25-660a-4202-81e4-9d4ea4e64ab9', 1, '50f4deaf-f922-4085-8e7b-9353525157a2'),
    ('9d463d25-660a-4202-81e4-9d4ea4e64ab9', 1, '526344b3-16bc-495a-8922-231f5c56a486'),
    ('9d463d25-660a-4202-81e4-9d4ea4e64ab9', 1, '4411d2fb-6820-4880-a332-db18c01bec9d'),
    ('9d463d25-660a-4202-81e4-9d4ea4e64ab9', 1, '1a9e26b7-69ff-45a0-8fa0-90e365b26caf'),
    ('5ff14d28-e07b-4f98-a63e-1397243a15b0', 1, '494dc36d-7cd0-49d1-bdca-9f73a94b695a'),
    ('5ff14d28-e07b-4f98-a63e-1397243a15b0', 1, 'd78456ce-686e-43ba-93f8-33d3a48966cc'),
    ('5ff14d28-e07b-4f98-a63e-1397243a15b0', 1, '0be939b5-4ba0-470c-92ab-299569a7e3a2'),
    ('5ff14d28-e07b-4f98-a63e-1397243a15b0', 1, 'aab22706-3e0a-47bf-b74e-75bb8f0fb173'),
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, '602c0d62-2e9e-4967-9e10-f0e7a1e3dbc2'),
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, 'ce9e8c8d-4bfd-4d9b-aaed-acb51db450a2'),
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, '82905c10-f71f-4336-a66e-d13b8fb803ca'),
    ('11a3de8c-7a9b-43d7-bbc0-b5d3a62fe59e', 1, '00df183c-ae15-4cb9-b32a-1d6870341db6'),
    ('11a3de8c-7a9b-43d7-bbc0-b5d3a62fe59e', 1, '6902bec8-fea7-4eb0-a00e-ef6f5cdea795'),
    ('11a3de8c-7a9b-43d7-bbc0-b5d3a62fe59e', 1, '6e8898b8-f5e7-4c6b-90e8-ab2445aba055'),
    ('11a3de8c-7a9b-43d7-bbc0-b5d3a62fe59e', 1, '1a0618a9-4199-4981-b3c0-992a9b36b297'),
    ('ef58edcf-2fe7-4595-bdf6-9ef029ff4a98', 1, 'fa3cd08d-d1f6-41b8-be50-0d419d60fbf3'),
    ('ef58edcf-2fe7-4595-bdf6-9ef029ff4a98', 1, 'a81fc815-4e85-4d9d-8d31-b30967676135'),
    ('327b9456-b9da-42e1-86c3-f4b25f38a0e9', 1, 'f5c8cfc5-fc71-4b9e-868d-29619317bb39'),
    ('327b9456-b9da-42e1-86c3-f4b25f38a0e9', 1, '4508d78e-6662-470f-b1ad-df4eb3c6126d'),
    ('327b9456-b9da-42e1-86c3-f4b25f38a0e9', 1, 'd46d408c-3b1b-4384-9f8f-19fae10f8ae9'),
    ('327b9456-b9da-42e1-86c3-f4b25f38a0e9', 1, 'cb849815-5afb-4d82-b7e6-1bf61b8339a4'),
    ('ae8fd23a-1347-4f16-9295-d3cba2721db1', 1, '8d447e19-1282-47e5-881e-9989c3101b86'),
    ('ae8fd23a-1347-4f16-9295-d3cba2721db1', 1, '062cc5ab-9943-4645-ade7-d356c9d8c420'),
    ('ae8fd23a-1347-4f16-9295-d3cba2721db1', 1, 'c4d5ff87-a22a-4c9c-bdca-4283df1e7e60'),
    ('ae8fd23a-1347-4f16-9295-d3cba2721db1', 1, 'dc656600-f7e3-4314-8b37-2b453afb867e'),
    ('15d6c5fa-f00e-41e3-8cc2-84b05ed9bcb5', 1, 'ee32b8a2-8fb0-468d-a896-fb5be6f58e98'),
    ('15d6c5fa-f00e-41e3-8cc2-84b05ed9bcb5', 1, 'fc7bc0bf-af00-4ddb-9bc0-950b349beff5'),
    ('15d6c5fa-f00e-41e3-8cc2-84b05ed9bcb5', 1, 'a6e863bb-f119-4210-9786-6229fe63c80f'),
    ('72dbf31f-e7bb-43d5-890d-bde5c83ef03e', 1, '17364753-0942-494c-a1b9-d116d60270f0'),
    ('72dbf31f-e7bb-43d5-890d-bde5c83ef03e', 1, 'b8429933-2293-4e90-b880-62fbd7096da8'),
    ('72dbf31f-e7bb-43d5-890d-bde5c83ef03e', 1, '0781ebb4-fd0d-4b6a-a180-1f388febc983'),
    ('cb64a27b-28d0-4b99-8241-cedfe014a635', 1, '57c36174-b932-45f7-8e43-886b3088bc98'),
    ('cb64a27b-28d0-4b99-8241-cedfe014a635', 1, '274ae25d-b60b-4cb8-b439-66e76f0e0d63'),
    ('cb64a27b-28d0-4b99-8241-cedfe014a635', 1, '54f4dfb4-5f95-4f86-b1e7-1ca2de384a5f'),
    ('b42d2a1a-b118-49ea-b83a-b1aee1b67e2e', 1, '35779921-bf6b-4afa-9cc6-f57a2a6b1c71'),
    ('b42d2a1a-b118-49ea-b83a-b1aee1b67e2e', 1, '87827603-7ff6-4fd6-a40d-184772ab9fc9'),
    ('a39fc8ad-becf-4d18-b79e-074fa9d6201a', 1, '29d39ef4-ebba-4aa9-b3ac-90cf5c71904f'),
    ('a39fc8ad-becf-4d18-b79e-074fa9d6201a', 1, 'b0b44dc8-f843-471d-ad9d-f36bec5c46e8'),
    ('6f2fd5b4-69de-4c95-92f2-02d67d81c204', 1, 'e7747695-7816-439f-8b2e-f7c26032dfb0'),
    ('6f2fd5b4-69de-4c95-92f2-02d67d81c204', 1, 'fb3f938c-89b5-43e3-bef7-155e093baa6b'),
    ('d57c1395-694b-4e69-8f01-f6a5b062502b', 1, '2797109e-d2f9-4f5f-85c9-64e49ebdccca'),
    ('d57c1395-694b-4e69-8f01-f6a5b062502b', 1, '7a9d2ce2-2dc9-422b-b412-0cf0468be052'),
    ('74aee64e-e8e5-490a-90c0-991d76c76752', 1, '1cb2f813-0d79-404a-9906-53a55ac30ada'),
    ('74aee64e-e8e5-490a-90c0-991d76c76752', 1, '163f8056-7e13-4d4a-92fa-d30d8d2ec8b4'),
    ('74aee64e-e8e5-490a-90c0-991d76c76752', 1, '09b04400-d0d0-418a-abd7-c1fc980013b0'),
    ('74aee64e-e8e5-490a-90c0-991d76c76752', 1, 'd4897f35-d50c-4878-9eaa-78b90e3eef0e'),
    ('7ffdb1b9-36a7-4d5c-a1d4-d37a4cfa9282', 1, '867b243b-512b-43a1-ac10-5393dcc598d9'),
    ('7ffdb1b9-36a7-4d5c-a1d4-d37a4cfa9282', 1, '61a55d22-5b06-4cb8-9c28-3e3ffd1a4b79'),
    ('7ffdb1b9-36a7-4d5c-a1d4-d37a4cfa9282', 1, '28ed61be-0016-4192-aa2b-73fa4361102a'),
    ('9ea67f4b-50fd-4b4b-b0cc-693e35d5dbb5', 1, '085e32f5-9cb8-466a-947c-ab589553708e'),
    ('9ea67f4b-50fd-4b4b-b0cc-693e35d5dbb5', 1, '0eea864b-88dc-42c3-b520-16fef4720689'),
    ('9ea67f4b-50fd-4b4b-b0cc-693e35d5dbb5', 1, 'df1054a0-08e5-4db8-adf0-84d68ce8fe05'),
    ('9ea67f4b-50fd-4b4b-b0cc-693e35d5dbb5', 1, 'daada59b-7cce-4570-99cb-59b5e64ef7d1'),
    ('a3f5b299-1bc5-45ec-bb6e-b564e0d11c94', 1, '29a54183-e025-45ca-a12c-01a54982fa03'),
    ('a3f5b299-1bc5-45ec-bb6e-b564e0d11c94', 1, 'cea742f3-982f-4252-a212-67a0d1aef1d3'),
    ('bf00b4b0-b3d9-4660-87c5-97f2b4159be0', 1, '4390fefd-3e8b-4c51-b731-24c9a62e7a62'),
    ('bf00b4b0-b3d9-4660-87c5-97f2b4159be0', 1, '281db6c2-da94-4c36-b207-7c219ca5673a'),
    ('bf00b4b0-b3d9-4660-87c5-97f2b4159be0', 1, 'a8c86335-acbb-4451-8476-c9d7a175b9b2'),
    ('bf00b4b0-b3d9-4660-87c5-97f2b4159be0', 1, '57359b28-7fd2-45aa-a8a4-0928464c7abe'),
    ('c78d7030-5204-4734-b92f-62c9e7040b33', 1, '910753d6-3c7a-420d-8caf-143a60ea91a0'),
    ('c78d7030-5204-4734-b92f-62c9e7040b33', 1, 'a2386b8c-c425-4829-bcba-9dd85cf02326'),
    ('c78d7030-5204-4734-b92f-62c9e7040b33', 1, '0965f130-55df-4fd6-a1d4-62e3dbf88cfc'),
    ('36d5e0a5-e3ea-4604-bf3c-467d62b35ec6', 1, 'a9d19b53-2e18-4a68-b14a-8f38e70e67cf'),
    ('36d5e0a5-e3ea-4604-bf3c-467d62b35ec6', 1, '9db71551-c825-4def-99fb-4a1cf998226e'),
    ('36d5e0a5-e3ea-4604-bf3c-467d62b35ec6', 1, '741a8def-9079-4c31-a3db-a98354cf85b6'),
    ('36d5e0a5-e3ea-4604-bf3c-467d62b35ec6', 1, 'b6ac7541-d754-4e11-8307-9ccd2b96d459'),
    ('1b74c3b4-12fc-48ea-b7b3-55600d1f5c78', 1, 'c16643c3-fa46-493c-abb1-306893ac2535'),
    ('1b74c3b4-12fc-48ea-b7b3-55600d1f5c78', 1, '379e6f95-6a78-46e8-9211-c63ef88672a5'),
    ('1b74c3b4-12fc-48ea-b7b3-55600d1f5c78', 1, '1dbf6a53-4e5a-465f-b787-31c6fdd4b044'),
    ('1b74c3b4-12fc-48ea-b7b3-55600d1f5c78', 1, '94780926-ec20-46cf-81a5-231b852dc01f'),
    ('13b86db3-3e28-4f01-bb8b-97d9d0a7d5c1', 1, '28f0a762-6e1b-45ae-82be-cb4ea474b40f'),
    ('13b86db3-3e28-4f01-bb8b-97d9d0a7d5c1', 1, '1c681368-a284-4a92-9143-e5822c63fd8b'),
    ('13b86db3-3e28-4f01-bb8b-97d9d0a7d5c1', 1, 'd188b669-6f2f-432e-91e8-1685379f0556'),
    ('3c4a95b6-f13e-4a1a-b37e-dba49f72c9f6', 1, 'c48bf884-fc3f-4e4d-8e16-7c0268fa2544'),
    ('3c4a95b6-f13e-4a1a-b37e-dba49f72c9f6', 1, '6489e77b-0b14-463e-9431-2936e3a4a668'),
    ('3c4a95b6-f13e-4a1a-b37e-dba49f72c9f6', 1, '2f5f5b57-6665-46c9-a79e-45a2454a672c'),
    ('3c4a95b6-f13e-4a1a-b37e-dba49f72c9f6', 1, '3dbba8cf-2d28-4e25-8ad9-af9debf14f33'),
    ('6a9cd799-4de1-4d3f-a70e-70f5157029b3', 1, '1ae22215-01c3-4452-9e1b-2b358b574f76'),
    ('6a9cd799-4de1-4d3f-a70e-70f5157029b3', 1, 'a365c087-64cf-4818-974a-a8993bad2ff0'),
    ('6a9cd799-4de1-4d3f-a70e-70f5157029b3', 1, '5f903937-c87b-40cc-9301-4c1a4886bccc'),
    ('6a9cd799-4de1-4d3f-a70e-70f5157029b3', 1, 'b351fde8-94b8-463a-b89b-adbb0ae6ca50'),
    ('8c829577-d7d1-4ea5-b983-e21a41307947', 1, '2626a12a-7b80-494c-820b-69e0d3018938'),
    ('8c829577-d7d1-4ea5-b983-e21a41307947', 1, 'b2999e54-04e9-4ed7-8083-0f45c795a3e6'),
    ('8c829577-d7d1-4ea5-b983-e21a41307947', 1, '6c02bedb-5785-43cb-81d7-82a18f3911f8'),
    ('7b5912f7-d29a-4f60-a9c9-9e8d8acbadae', 1, '37f9d551-96a4-47e4-80bd-a3bc46281524'),
    ('7b5912f7-d29a-4f60-a9c9-9e8d8acbadae', 1, '654ce7c8-c3df-424f-973e-aec884823680'),
    ('7b5912f7-d29a-4f60-a9c9-9e8d8acbadae', 1, '450c074b-2ca1-434d-b353-0eddb6a9e25e'),
    ('7e9d4ad7-b82c-43b5-9a4c-9e343f038779', 1, '28532498-a2ca-43c6-9301-5c0b1dbb18bb'),
    ('7e9d4ad7-b82c-43b5-9a4c-9e343f038779', 1, 'b34a5b62-5085-4299-819b-f9f2790a649c'),
    ('7e9d4ad7-b82c-43b5-9a4c-9e343f038779', 1, '18b60979-24af-4f46-8c91-b8d0252850a8'),
    ('e1c82607-b99a-41c4-8cd0-e09e4cc9f93d', 1, '833b0d7b-06e0-429e-add9-16df76a1165d'),
    ('e1c82607-b99a-41c4-8cd0-e09e4cc9f93d', 1, 'd627ff6c-fc7c-4cee-ac24-55f8080c16a6'),
    ('e1c82607-b99a-41c4-8cd0-e09e4cc9f93d', 1, 'ebad4013-c85b-484f-ad1a-09e1ddfc4702'),
    ('e1c82607-b99a-41c4-8cd0-e09e4cc9f93d', 1, 'b3dc3eb5-3193-4f7e-bdca-e2b14b5cb84c'),
    ('ac53c13a-d9cf-46b4-bdf9-3f9a4477e1ed', 1, 'a27179e4-ae98-495e-a8f6-840408afe6de'),
    ('ac53c13a-d9cf-46b4-bdf9-3f9a4477e1ed', 1, '04ef6b70-d0db-4f81-97d8-62096ceb9eae'),
    ('9f0d0870-1f5c-4067-b2f5-fb22a2fe03e3', 1, '9e53dd92-bf60-49c0-a1f8-57e921143741'),
    ('9f0d0870-1f5c-4067-b2f5-fb22a2fe03e3', 1, '590fdcf6-960d-4b80-9839-36e1aec13188'),
    ('c92a9ef9-c5e6-4ac5-b990-1c499f7a9cc3', 1, 'd22e863f-592d-4431-ac76-bf1041a25056'),
    ('c92a9ef9-c5e6-4ac5-b990-1c499f7a9cc3', 1, '7decfcd5-075f-4c8e-965a-4d3c5bc1f356');

INSERT INTO event_organizer_favourite_products (id, static_product_id)
VALUES
    -- EVENT ORGANIZER FAVOURITE PRODUCTS
    ('b38d716b-4d2a-4fd3-b18c-bfa128f24b99', '763b0d82-81de-4a8c-8bba-45c19e688b31'),
    ('b38d716b-4d2a-4fd3-b18c-bfa128f24b99', '24a7cf0a-8a8b-46ad-bb23-f4c8be7b154d'),
    ('b38d716b-4d2a-4fd3-b18c-bfa128f24b99', 'a3f5b299-1bc5-45ec-bb6e-b564e0d11c94'),
    ('47c5fa7c-0d12-48e2-a4ed-9e4f441b383f', '13b86db3-3e28-4f01-bb8b-97d9d0a7d5c1'),
    ('47c5fa7c-0d12-48e2-a4ed-9e4f441b383f', 'ac53c13a-d9cf-46b4-bdf9-3f9a4477e1ed'),
    ('47c5fa7c-0d12-48e2-a4ed-9e4f441b383f', 'a39fc8ad-becf-4d18-b79e-074fa9d6201a'),
    ('9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0', 'c78d7030-5204-4734-b92f-62c9e7040b33'),
    ('9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0', '6f2fd5b4-69de-4c95-92f2-02d67d81c204'),
    ('9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0', 'c92a9ef9-c5e6-4ac5-b990-1c499f7a9cc3'),
    ('1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236', '327b9456-b9da-42e1-86c3-f4b25f38a0e9'),
    ('1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236', '74aee64e-e8e5-490a-90c0-991d76c76752'),
    ('1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236', 'b94869d7-c5cf-4ff2-8b9a-2d26cd722ba7');

-- SERVICES

INSERT INTO service_category (id, description, is_deleted, is_pending, name)
VALUES
    ('f9963f95-9f22-4320-905d-5d0641bd3335', '', false, true, 'Associated category 2'),
    ('cb03b66c-8ee4-4f4e-be96-e91164ea0f8d', '', false, true, 'Service category 2'),
    ('574a6ad5-16d0-4b42-9a4a-e39b0673843c', '', false, true, 'Service category 3'),
    ('a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3', 'Music performance by bands, solo artists or DJ-s for events.', false, false, 'Music'),
    ('d46e1f95-8a90-4745-8000-629f412bdbab', 'Event catering services, including food preparation and serving staff.', false, false, 'Catering'),
    ('6b351a75-3061-4d96-8856-d58f1576a568', 'Photography services for events, including group photos and candid shots.', false, false, 'Photography'),
    ('3d5cb7b1-e512-4eae-bcd9-c2954b643b1b', 'Videography services to capture key moments during the event.', false, false, 'Videography'),
    ('f9c3bc34-6316-47a1-b61a-85f842f8a76d', 'Transportation service for guests, including buses, shuttles, and private vehicles.', false, false, 'Guest Transportation'),
    ('3d0107f7-2cfa-4e95-b5b1-0136034602b6', 'Security services including crowd control, event access monitoring, and safety personnel.', false, false, 'Event Security');

INSERT INTO static_service (static_service_id, service_category_id, pending, seller_id)
VALUES
    -- MUSIC
    ('daa22294-5377-487a-aa3f-7cd5a42cc568', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('195eb1c5-6fd6-4139-a697-8cd906219525', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3', false, 'a1d764df-9b5c-4f62-b0a1-13d8edfcf4a3'),
    ('c3210396-e7f0-445a-9c26-9b1aec7a3c4a', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('e30780b8-b7a8-4737-ae5e-cd11a9ad29fa', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),
    ('a43f5588-d452-4723-a1a1-f5fae353aaab', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('7e0477b8-ae16-4bd1-9596-e4e4372d8c7e', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3', false, 'a1d764df-9b5c-4f62-b0a1-13d8edfcf4a3'),
    ('3ed7c6fd-155e-48a9-a376-52b9b3b43ad8', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('379624ba-652e-42e2-a7bb-d23a53ac2eed', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),
    ('f501fea8-7903-4ff0-a3d3-49493282a69e', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('fe2e0eb8-29fa-448b-aace-8af9ccb101f9', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3', false, 'a1d764df-9b5c-4f62-b0a1-13d8edfcf4a3'),

    ('db3fc51a-0775-44ba-b031-955503ed74d1', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('f6bb3ca6-cd9c-42dc-9fe0-fd0b84dd79ca', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('96997487-e316-46f0-8868-d795b80157ba', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),

    -- CATERING
    ('deca359b-9bfb-4b6f-bc24-3e509f595da4', 'd46e1f95-8a90-4745-8000-629f412bdbab', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('8ec60ce2-d646-43bf-abf1-01e2d6c5c202', 'd46e1f95-8a90-4745-8000-629f412bdbab', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),
    ('b965d715-d2f9-4471-a7f3-5dfb592cfe3d', 'd46e1f95-8a90-4745-8000-629f412bdbab', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),

    -- PHOTOGRAPHY
    ('82828d99-3ed9-4a71-8c91-ecfe040411a5', '6b351a75-3061-4d96-8856-d58f1576a568', false, 'a1d764df-9b5c-4f62-b0a1-13d8edfcf4a3'),
    ('ef1c7659-9bad-4225-b28e-f4b5c133ecbf', '6b351a75-3061-4d96-8856-d58f1576a568', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('245cb1b1-336b-4bf3-9b40-73eee616f2de', '6b351a75-3061-4d96-8856-d58f1576a568', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),
    ('9447c5d5-3a82-44e2-8fe3-5d836f0eda63', '6b351a75-3061-4d96-8856-d58f1576a568', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('9ee88634-aa10-48d1-b2c4-98556eac1684', '6b351a75-3061-4d96-8856-d58f1576a568', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('10c348f9-e96c-4073-8704-7012f2daa220', '6b351a75-3061-4d96-8856-d58f1576a568', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),
    ('1dfcfba8-1b39-4a38-8ff3-a3ec5b409ca2', '6b351a75-3061-4d96-8856-d58f1576a568', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),

    -- VIDEOGRAPHY
    ('26f74471-8c03-44a0-b200-8796b351f8aa', '3d5cb7b1-e512-4eae-bcd9-c2954b643b1b', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),
    ('888f99a9-a0e5-469c-bc21-2d6bd429b777', '3d5cb7b1-e512-4eae-bcd9-c2954b643b1b', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('2c358e12-19f2-4a16-a198-ece7c11f7863', '3d5cb7b1-e512-4eae-bcd9-c2954b643b1b', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('8fb67698-2344-4b1d-950e-478c14f477cd', '3d5cb7b1-e512-4eae-bcd9-c2954b643b1b', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),
    ('f38ad3b7-2b26-4762-9f64-892953ba5207', '3d5cb7b1-e512-4eae-bcd9-c2954b643b1b', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),

    -- GUEST TRANSPORTATION
    ('8d92004c-ce17-4248-ac60-e0a3750bf083', 'f9c3bc34-6316-47a1-b61a-85f842f8a76d', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),
    ('38db314c-ce21-4f96-a2b5-5a6284b1b7b1', 'f9c3bc34-6316-47a1-b61a-85f842f8a76d', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('b008b01e-4f96-4233-873e-77617645c371', 'f9c3bc34-6316-47a1-b61a-85f842f8a76d', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('f43f3ffe-66cb-431b-a88a-fe1b41dbbf22', 'f9c3bc34-6316-47a1-b61a-85f842f8a76d', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),
    ('fb6845b5-78c6-495f-b550-c043c9372dee', 'f9c3bc34-6316-47a1-b61a-85f842f8a76d', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),

    -- EVENT SECURITY
    ('faa74e7e-797a-44f8-8c80-31c0f7964e78', '3d0107f7-2cfa-4e95-b5b1-0136034602b6', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),
    ('0792d0dd-044d-43df-8031-5f9377522502', '3d0107f7-2cfa-4e95-b5b1-0136034602b6', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),
    ('9affc3a2-6ad9-4677-ae6f-dcc8c77d878b', '3d0107f7-2cfa-4e95-b5b1-0136034602b6', false, '6e0c99f4-226f-49fb-bc4b-1f59ff671b95'),
    ('a37de399-5404-4afb-b722-07b790f49ecc', '3d0107f7-2cfa-4e95-b5b1-0136034602b6', false, 'c5e2a004-83b0-4f91-9ff2-c235f2166b72'),
    ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', '3d0107f7-2cfa-4e95-b5b1-0136034602b6', false, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5'),

    -- PENDING SERVICE CATEGORY 1
    ('e98baa49-e3b0-49d9-830f-596b87d8fbfe', 'f9963f95-9f22-4320-905d-5d0641bd3335', true, '2b0cba7e-f6b9-4b28-9b92-48d5abfae6e5');

INSERT INTO versioned_service (static_service_id, version, cancellation_deadline, description, is_active, is_available, is_last_version, is_confirmation_manual, is_private, name, price, reservation_deadline, sale_percentage, maximum_duration, minimum_duration)
VALUES
    -- MUSIC
    ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, 15, 'A classic jazz band providing smooth live music perfect for cocktail hours or dinner events.', true, true, true, false, false, 'Classic Jazz Band', 890, 20, 0.0, 15, 15),
    ('195eb1c5-6fd6-4139-a697-8cd906219525', 1, 25, 'Solo pianist performing a repertoire of contemporary and classical music for upscale events.', true, true, true, false, false, 'Solo Pianist', 500, 15, 0.03, 165, 60),
    ('c3210396-e7f0-445a-9c26-9b1aec7a3c4a', 1, 19, 'Cover band playing hits from the 80s, 90s, and today for weddings, parties, and corporate events.', true, true, true, false, false, 'Cover Band', 130, 20, 0.04, 135, 30),
    ('e30780b8-b7a8-4737-ae5e-cd11a9ad29fa', 1, 18, 'Upbeat DJ with a wide selection of genres including EDM, pop, and hip-hop for weddings and parties.', true, true, true, false, false, 'Party DJ', 150, 20, 0.04, 150, 15),
    ('a43f5588-d452-4723-a1a1-f5fae353aaab', 1, 10, 'Acoustic duo providing live folk and indie music, ideal for intimate gatherings.', true, true, true, false, false, 'Acoustic Duo', 600, 12, 0.02, 345, 30),
    ('7e0477b8-ae16-4bd1-9596-e4e4372d8c7e', 1, 30, 'Rock band with high-energy performances, perfect for corporate events or large parties.', true, true, true, false, false, 'Rock Band', 200, 30, 0.07, 60, 60),
    ('3ed7c6fd-155e-48a9-a376-52b9b3b43ad8', 1, 20, 'Solo violinist performing classical pieces for formal events and ceremonies.', true, true, true, false, false, 'Solo Violinist', 400, 18, 0.01, 540, 30),
    ('379624ba-652e-42e2-a7bb-d23a53ac2eed', 1, 14, 'DJ specializing in house and techno music, suitable for nightclubs and high-energy events.', true, true, true, false, false, 'House DJ', 180, 15, 0.06, 210, 75),
    ('f501fea8-7903-4ff0-a3d3-49493282a69e', 1, 16, 'Jazz ensemble with a variety of instruments for high-class receptions and dinner events.', true, true, true, false, false, 'Jazz Ensemble', 120, 25, 0.05, 105, 105),
    ('fe2e0eb8-29fa-448b-aace-8af9ccb101f9', 1, 22, 'Solo guitarist offering a mix of classical and contemporary acoustic guitar for elegant gatherings.', true, true, true, false, false, 'Solo Guitarist', 550, 20, 0.03, 465, 30),

    ('db3fc51a-0775-44ba-b031-955503ed74d1', 1, 22, 'desc.', false, true, true, false, false, 'not active', 550, 20, 0.03, 465, 30),
    ('f6bb3ca6-cd9c-42dc-9fe0-fd0b84dd79ca', 1, 22, 'desc.', true, true, true, false, true, 'not public', 550, 20, 0.03, 465, 30),
    ('96997487-e316-46f0-8868-d795b80157ba', 1, 22, 'desc.', true, false, true, false, false, 'not available', 550, 20, 0.03, 465, 30),

    -- CATERING
    ('deca359b-9bfb-4b6f-bc24-3e509f595da4', 1, 25, 'Elegant plated dinner service for weddings and formal events.', true, true, true, false, false, 'Plated Dinner Service', 200, 15, 0.03, 105, 105),
    ('8ec60ce2-d646-43bf-abf1-01e2d6c5c202', 1, 18, 'Casual BBQ catering, perfect for outdoor events and summer parties.', true, true, true, false, false, 'BBQ Catering', 120, 20, 0.04, 30, 30),
    ('b965d715-d2f9-4471-a7f3-5dfb592cfe3d', 1, 19, 'Breakfast and brunch catering, perfect for morning events and meetings.', true, true, true, false, false, 'Brunch Catering', 110, 20, 0.04, 255, 90),

    -- PHOTOGRAPHY
    ('82828d99-3ed9-4a71-8c91-ecfe040411a5', 1, 15, 'Wedding photography service capturing candid and posed moments throughout the entire day, including pre-ceremony and reception.', true, true, true, false, false, 'Wedding Photography', 250, 20, 0.05, 60, 60),
    ('ef1c7659-9bad-4225-b28e-f4b5c133ecbf', 1, 25, 'Corporate event photography, including keynote speeches, networking sessions, and group photos.', true, true, true, false, false, 'Corporate Event Photography', 150, 15, 0.03, 135, 135),
    ('245cb1b1-336b-4bf3-9b40-73eee616f2de', 1, 18, 'Outdoor portrait photography session, offering a mix of natural lighting and candid poses in scenic locations.', true, true, true, false, false, 'Outdoor Portrait Photography', 600, 20, 0.04, 570, 30),
    ('9447c5d5-3a82-44e2-8fe3-5d836f0eda63', 1, 30, 'Family photography with customized themes and settings for large family gatherings and reunions.', true, true, true, false, false, 'Family Photography', 120, 30, 0.07, 195, 30),
    ('9ee88634-aa10-48d1-b2c4-98556eac1684', 1, 20, 'Event photography for private parties, birthdays, and celebrations, capturing key moments and group shots.', true, true, true, false, false, 'Event Photography', 800, 18, 0.01, 150, 30),
    ('10c348f9-e96c-4073-8704-7012f2daa220', 1, 16, 'Engagement photography with a mix of romantic portraits and candid shots to celebrate the couple.', true, true, true, false, false, 'Engagement Photography', 700, 25, 0.05, 450, 30),
    ('1dfcfba8-1b39-4a38-8ff3-a3ec5b409ca2', 1, 22, 'Newborn and maternity photography to capture beautiful moments for families during pregnancy and the early days of life.', true, true, true, false, false, 'Newborn & Maternity Photography', 900, 20, 0.03, 150, 150),

    -- VIDEOGRAPHY
    ('26f74471-8c03-44a0-b200-8796b351f8aa', 1, 15, 'Full-day wedding videography capturing every moment from preparations to the reception, including drone footage.', true, true, true, false, false, 'Wedding Videography', 300, 20, 0.05, 240, 240),
    ('888f99a9-a0e5-469c-bc21-2d6bd429b777', 1, 25, 'Corporate event videography, covering keynotes, interviews, and networking sessions with professional editing and highlights.', true, true, true, false, false, 'Corporate Event Videography', 180, 15, 0.03, 150, 45),
    ('2c358e12-19f2-4a16-a198-ece7c11f7863', 1, 18, 'Highlight reel video for birthdays and private parties, showcasing the best moments with a cinematic touch.', true, true, true, false, false, 'Event Highlight Videography', 120, 20, 0.04, 195, 30),
    ('8fb67698-2344-4b1d-950e-478c14f477cd', 1, 10, 'Live streaming videography for events, including weddings, conferences, or other occasions, with multi-camera setup.', true, true, true, false, false, 'Live Streaming Videography', 150, 12, 0.02, 60, 60),
    ('f38ad3b7-2b26-4762-9f64-892953ba5207', 1, 22, 'Maternity and baby shower videography, documenting key moments and emotional speeches during the event.', true, true, true, false, false, 'Maternity & Baby Shower Videography', 750, 20, 0.03, 90, 90),

    -- GUEST TRANSPORTATION
    ('8d92004c-ce17-4248-ac60-e0a3750bf083', 1, 15, 'Luxury limousine service for weddings, including red carpet, drinks, and personalized decor.', true, true, true, false, false, 'Wedding Limousine', 150, 20, 0.05, 210, 45),
    ('38db314c-ce21-4f96-a2b5-5a6284b1b7b1', 1, 25, 'Shuttle bus service for corporate events, transporting attendees between venues or from hotels to event sites.', true, true, true, false, false, 'Corporate Shuttle Service', 120, 15, 0.03, 60, 60),
    ('b008b01e-4f96-4233-873e-77617645c371', 1, 18, 'Private car service for VIP guests at events, offering comfortable and discreet transportation in luxury vehicles.', true, true, true, false, false, 'VIP Car Service', 100, 20, 0.04, 195, 150),
    ('f43f3ffe-66cb-431b-a88a-fe1b41dbbf22', 1, 22, 'Helicopter transport service for exclusive events, offering quick and luxurious travel to venues with a scenic view.', true, true, true, false, false, 'Helicopter Transport Service', 500, 20, 0.03, 45, 45),
    ('fb6845b5-78c6-495f-b550-c043c9372dee', 1, 19, 'Luxury van rental for private parties, ensuring that guests are comfortably transported in style between event locations.', true, true, true, false, false, 'Luxury Van Rental', 150, 20, 0.04, 90, 90),

    -- EVENT SECURITY
    ('faa74e7e-797a-44f8-8c80-31c0f7964e78', 1, 15, 'Professional security personnel for weddings, ensuring guest safety and managing crowd control during the event.', true, true, true, false, false, 'Wedding Security', 120, 20, 0.05, 75, 75),
    ('0792d0dd-044d-43df-8031-5f9377522502', 1, 25, 'Event security for corporate conferences and expos, providing entry control, monitoring, and incident response.', true, true, true, false, false, 'Corporate Event Security', 250, 15, 0.0, 60, 60),
    ('9affc3a2-6ad9-4677-ae6f-dcc8c77d878b', 1, 10, 'Crowd management and security for concerts and festivals, ensuring smooth entry, exits, and maintaining order throughout the event.', true, true, true, false, false, 'Concert & Festival Security', 180, 12, 0.02, 255, 15),
    ('a37de399-5404-4afb-b722-07b790f49ecc', 1, 16, 'Security for luxury events and gala dinners, offering discreet yet effective protection for high-profile venues and attendees.', true, true, true, false, false, 'Gala Event Security', 120, 25, 0.05, 75, 75),
    ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, 19, 'Bouncer services for clubs, bars, and private events, ensuring safety and managing entrance to maintain a secure atmosphere.', true, true, true, false, false, 'Bouncer Security', 900, 20, 0.04, 180, 45),

    -- PENDING SERVICE CATEGORY 1
    ('e98baa49-e3b0-49d9-830f-596b87d8fbfe', 1, 19, 'Pending service 1', true, true, true, false, false, 'Pending service 1', 900, 20, 0.04, 60, 60);

INSERT INTO versioned_service_images(versioned_service_static_service_id, versioned_service_version, images)
VALUES ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, '4c1e5662-32aa-40d0-8905-321e285055bf'),
       ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, 'cc9f9d97-5d9d-4220-ae4d-dd71b34387aa'),
       ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, 'd25d1686-abe5-475b-9f54-fce2569e2d63'),
       ('195eb1c5-6fd6-4139-a697-8cd906219525', 1, 'effb4985-edb9-45ff-80bd-25906c05d056'),
       ('195eb1c5-6fd6-4139-a697-8cd906219525', 1, '847ac1b3-d1a4-4db0-a3c9-5bca02a9f188'),
       ('195eb1c5-6fd6-4139-a697-8cd906219525', 1, 'cf245d4c-36cd-4f61-af82-aa3a12647724'),
       ('c3210396-e7f0-445a-9c26-9b1aec7a3c4a', 1, '7d655b05-5285-4a52-93f6-008c5652518f'),
       ('c3210396-e7f0-445a-9c26-9b1aec7a3c4a', 1, '70a85aa5-6f8e-41c0-bffb-0665b119377a'),
       ('c3210396-e7f0-445a-9c26-9b1aec7a3c4a', 1, 'aa590637-8cdb-49ed-93ed-ccd7d0b041eb'),
       ('e30780b8-b7a8-4737-ae5e-cd11a9ad29fa', 1, 'c70ac293-ca12-4cd6-905f-8172ce8163d1'),
       ('e30780b8-b7a8-4737-ae5e-cd11a9ad29fa', 1, '211d5085-a95d-413f-bd7b-ef7a063216c3'),
       ('a43f5588-d452-4723-a1a1-f5fae353aaab', 1, 'ac42a75f-cda7-4552-98c2-360c215420a7'),
       ('a43f5588-d452-4723-a1a1-f5fae353aaab', 1, 'e00fd049-5f05-4167-88de-0f6dfd6da4f7'),
       ('7e0477b8-ae16-4bd1-9596-e4e4372d8c7e', 1, 'a731a9ab-7a21-441c-a6ab-d4df055fc729'),
       ('7e0477b8-ae16-4bd1-9596-e4e4372d8c7e', 1, 'f6cb448f-b420-4a7b-b208-025ea2a47690'),
       ('3ed7c6fd-155e-48a9-a376-52b9b3b43ad8', 1, 'f15bf9c0-95c5-4021-8fa5-a16fd9c1cdd7'),
       ('3ed7c6fd-155e-48a9-a376-52b9b3b43ad8', 1, '503a2425-2f69-44e0-adf0-c672f3e665a8'),
       ('3ed7c6fd-155e-48a9-a376-52b9b3b43ad8', 1, '78489458-2575-45f4-99a9-9ea20a3f2142'),
       ('3ed7c6fd-155e-48a9-a376-52b9b3b43ad8', 1, 'ea0321b6-028f-41ee-8d67-4ada9282c3f3'),
       ('379624ba-652e-42e2-a7bb-d23a53ac2eed', 1, '8393c800-70ac-44f4-80a4-1fc5b7bda1fd'),
       ('379624ba-652e-42e2-a7bb-d23a53ac2eed', 1, '1c057c0c-7ce2-410a-884e-3f059eed94de'),
       ('379624ba-652e-42e2-a7bb-d23a53ac2eed', 1, '8c92e9e2-8983-4560-87f0-b2b5c5112937'),
       ('379624ba-652e-42e2-a7bb-d23a53ac2eed', 1, 'de7708b5-380b-4076-9819-075491128036'),
       ('f501fea8-7903-4ff0-a3d3-49493282a69e', 1, 'ae0d37fd-1fee-40a7-8144-bd553f3abf26'),
       ('f501fea8-7903-4ff0-a3d3-49493282a69e', 1, '34fe3c79-58a1-4146-9144-edda5b3e8301'),
       ('f501fea8-7903-4ff0-a3d3-49493282a69e', 1, 'ee3794d5-2819-4266-9e93-19486e7f5915'),
       ('fe2e0eb8-29fa-448b-aace-8af9ccb101f9', 1, '2c841e53-1cf5-4d69-9708-b44939ecdabb'),
       ('fe2e0eb8-29fa-448b-aace-8af9ccb101f9', 1, '5b76660f-d256-4470-ba07-432fa17c2504'),
       ('fe2e0eb8-29fa-448b-aace-8af9ccb101f9', 1, '34fedaed-65a8-4684-b7e9-e94317bfff57'),
       ('deca359b-9bfb-4b6f-bc24-3e509f595da4', 1, 'b192e84b-e2c9-4bcd-a05e-bb2e5276d894'),
       ('deca359b-9bfb-4b6f-bc24-3e509f595da4', 1, 'f2f3fc5b-863a-4d77-adb0-e738440b4a6d'),
       ('deca359b-9bfb-4b6f-bc24-3e509f595da4', 1, 'aadd0225-2419-4eb2-a35d-ed3261222e2d'),
       ('deca359b-9bfb-4b6f-bc24-3e509f595da4', 1, 'ffdb47b3-8d90-4c9a-bad3-2f7699106328'),
       ('8ec60ce2-d646-43bf-abf1-01e2d6c5c202', 1, '1fb9a476-e556-4706-b404-083ea9d91646'),
       ('8ec60ce2-d646-43bf-abf1-01e2d6c5c202', 1, 'e7fa1911-2b4d-4c8e-90aa-f5c153c8af24'),
       ('8ec60ce2-d646-43bf-abf1-01e2d6c5c202', 1, '9b517d86-8c65-41e4-afa7-12b1e993bcbc'),
       ('b965d715-d2f9-4471-a7f3-5dfb592cfe3d', 1, '7e50817d-105c-48cc-a9ca-bfdf2a09da97'),
       ('b965d715-d2f9-4471-a7f3-5dfb592cfe3d', 1, '66b338f5-e931-4cdc-98c4-fae1e985173b'),
       ('b965d715-d2f9-4471-a7f3-5dfb592cfe3d', 1, '7e2f57bc-af76-4cae-857e-f36ee5cad78f'),
       ('82828d99-3ed9-4a71-8c91-ecfe040411a5', 1, 'ebfea400-7ef4-43ed-a5d8-c996014e3de6'),
       ('82828d99-3ed9-4a71-8c91-ecfe040411a5', 1, '9785a836-5b14-49f0-bf91-2d4f4ea534ca'),
       ('82828d99-3ed9-4a71-8c91-ecfe040411a5', 1, 'c7401800-fa62-4137-832c-4c8cc0983a9c'),
       ('82828d99-3ed9-4a71-8c91-ecfe040411a5', 1, '5d173281-0ac4-432f-a53b-f5eaab79c407'),
       ('ef1c7659-9bad-4225-b28e-f4b5c133ecbf', 1, '4f074734-86ac-4700-9245-eee14e9f91e5'),
       ('ef1c7659-9bad-4225-b28e-f4b5c133ecbf', 1, 'ef75748d-420f-4cb8-980f-369b1653fdf5'),
       ('ef1c7659-9bad-4225-b28e-f4b5c133ecbf', 1, '9ed76139-1d9c-4e72-a478-7c17ee25866a'),
       ('245cb1b1-336b-4bf3-9b40-73eee616f2de', 1, 'd8dcb2b8-c02a-4825-b81a-d2028e38e343'),
       ('245cb1b1-336b-4bf3-9b40-73eee616f2de', 1, 'ecb8d9e9-8686-4505-819c-53727525c3e7'),
       ('245cb1b1-336b-4bf3-9b40-73eee616f2de', 1, '9962089e-969e-42bf-a9cb-aba321c85907'),
       ('9447c5d5-3a82-44e2-8fe3-5d836f0eda63', 1, '453d40ef-1b37-4155-84ff-ca9a1cb5d91a'),
       ('9447c5d5-3a82-44e2-8fe3-5d836f0eda63', 1, '52df3fbd-fe33-4c6f-8abd-991292fcf433'),
       ('9447c5d5-3a82-44e2-8fe3-5d836f0eda63', 1, 'f154cd65-c712-4976-a17f-55914a9db4e3'),
       ('9ee88634-aa10-48d1-b2c4-98556eac1684', 1, '91789957-da4e-4d34-85da-4521674bd3d5'),
       ('9ee88634-aa10-48d1-b2c4-98556eac1684', 1, '8e700412-d5a7-45ce-906a-50d9bc504cbc'),
       ('10c348f9-e96c-4073-8704-7012f2daa220', 1, 'ab8cea8a-116b-4a01-9c2b-b5a333aa3fa6'),
       ('10c348f9-e96c-4073-8704-7012f2daa220', 1, '12fcd768-8c1f-49ff-810f-20953a1f24d7'),
       ('10c348f9-e96c-4073-8704-7012f2daa220', 1, '1126920c-f900-422f-a67f-7fc54337a83f'),
       ('10c348f9-e96c-4073-8704-7012f2daa220', 1, '8df5dd9f-d206-4b28-903d-ed603c746c0f'),
       ('1dfcfba8-1b39-4a38-8ff3-a3ec5b409ca2', 1, 'd285ce69-b62c-41ce-a016-f1df9b7e9b3d'),
       ('1dfcfba8-1b39-4a38-8ff3-a3ec5b409ca2', 1, '1217c0e1-a444-477f-85ed-221c1cef4b7d'),
       ('1dfcfba8-1b39-4a38-8ff3-a3ec5b409ca2', 1, '58425c83-f6a6-415b-b687-5f4ee88993f0'),
       ('1dfcfba8-1b39-4a38-8ff3-a3ec5b409ca2', 1, 'f01ccfb7-e15b-41de-88af-d01f6d65c89b'),
       ('26f74471-8c03-44a0-b200-8796b351f8aa', 1, 'a3243811-51fc-4e45-8f0f-6910c9e61ea7'),
       ('26f74471-8c03-44a0-b200-8796b351f8aa', 1, 'd29fb162-4299-47f7-bb9e-991b5e0e29cc'),
       ('26f74471-8c03-44a0-b200-8796b351f8aa', 1, '22790452-f577-45b6-850d-19cf77465764'),
       ('26f74471-8c03-44a0-b200-8796b351f8aa', 1, '179cb4a7-b9ac-4f65-9e39-5dcb09e73031'),
       ('888f99a9-a0e5-469c-bc21-2d6bd429b777', 1, 'fce30440-f562-46d7-8485-84c75c467b9b'),
       ('888f99a9-a0e5-469c-bc21-2d6bd429b777', 1, 'b5151d69-b196-4a42-9e12-ff5c9d853865'),
       ('888f99a9-a0e5-469c-bc21-2d6bd429b777', 1, '57fc63ec-cb73-4777-a822-70918a697e9d'),
       ('888f99a9-a0e5-469c-bc21-2d6bd429b777', 1, 'c918e1bf-87eb-4346-88c1-31fb932faf2e'),
       ('2c358e12-19f2-4a16-a198-ece7c11f7863', 1, 'ce43a69b-19f7-4802-9039-573a01c0f1e7'),
       ('2c358e12-19f2-4a16-a198-ece7c11f7863', 1, '9a409e15-fc8f-4b6a-ab1a-9ecc89d33a89'),
       ('2c358e12-19f2-4a16-a198-ece7c11f7863', 1, '7ab2672e-2cc2-4985-add0-179779059bbb'),
       ('2c358e12-19f2-4a16-a198-ece7c11f7863', 1, '02956ffe-ec41-4c6e-9f48-1802d3576012'),
       ('8fb67698-2344-4b1d-950e-478c14f477cd', 1, '75ac6547-3d40-4ae2-abbf-f8b72655967f'),
       ('8fb67698-2344-4b1d-950e-478c14f477cd', 1, 'd8b635bc-5c85-4b0c-bdbe-3df4e99c6fe0'),
       ('8fb67698-2344-4b1d-950e-478c14f477cd', 1, '47cc1bb2-6e12-4559-b56a-c4ed40b0a2a9'),
       ('8fb67698-2344-4b1d-950e-478c14f477cd', 1, '56e1afdb-5a4c-4205-b096-a3c79d17c7e2'),
       ('f38ad3b7-2b26-4762-9f64-892953ba5207', 1, 'c81d626f-0bed-4b3c-8744-cc3237abd442'),
       ('f38ad3b7-2b26-4762-9f64-892953ba5207', 1, '3865d1bd-be1b-48b9-b63f-21b9f472e0b4'),
       ('f38ad3b7-2b26-4762-9f64-892953ba5207', 1, '2b75e10a-2953-4053-b43f-18c1858ea78c'),
       ('f38ad3b7-2b26-4762-9f64-892953ba5207', 1, '157248df-7517-4c9c-b4c8-ccb7b63fa2c5'),
       ('8d92004c-ce17-4248-ac60-e0a3750bf083', 1, '9e7b4642-1d1e-4eb6-bdc2-132194f1acc6'),
       ('8d92004c-ce17-4248-ac60-e0a3750bf083', 1, '86489a6b-f61b-4fa4-9780-b7e0d61e02a4'),
       ('8d92004c-ce17-4248-ac60-e0a3750bf083', 1, 'e7d2bed4-c094-4f65-aaab-21529cf4055b'),
       ('8d92004c-ce17-4248-ac60-e0a3750bf083', 1, 'a0649b95-1305-4324-875d-45517354cf8e'),
       ('38db314c-ce21-4f96-a2b5-5a6284b1b7b1', 1, '1b9c9a18-c360-44bc-aeb5-f8c69591e177'),
       ('38db314c-ce21-4f96-a2b5-5a6284b1b7b1', 1, '24beabea-1b41-4838-bf97-2ffe7bfb5d00'),
       ('38db314c-ce21-4f96-a2b5-5a6284b1b7b1', 1, '93406168-c418-4e2c-a73e-ae4984f02440'),
       ('38db314c-ce21-4f96-a2b5-5a6284b1b7b1', 1, '2ca06699-63ec-452c-be48-9793e2322da3'),
       ('b008b01e-4f96-4233-873e-77617645c371', 1, '20153c3b-d3fc-4dd0-b60d-3dd509ea37e0'),
       ('b008b01e-4f96-4233-873e-77617645c371', 1, '4a0f3bb4-e5fb-431e-862e-35e3c254a79f'),
       ('f43f3ffe-66cb-431b-a88a-fe1b41dbbf22', 1, '9cb1ea40-385c-412e-a703-1a3dab7efbfa'),
       ('f43f3ffe-66cb-431b-a88a-fe1b41dbbf22', 1, 'd5ed6f36-1d84-4c1f-b01d-1528d727bf75'),
       ('fb6845b5-78c6-495f-b550-c043c9372dee', 1, 'bca9cb5d-f5b1-4977-9d22-b46541c088d0'),
       ('fb6845b5-78c6-495f-b550-c043c9372dee', 1, '8afa5f8d-b4ac-4bf4-9e8e-a37261e6d84b'),
       ('faa74e7e-797a-44f8-8c80-31c0f7964e78', 1, 'a541d00b-9bb0-4957-87fd-449080728261'),
       ('faa74e7e-797a-44f8-8c80-31c0f7964e78', 1, 'f49a834f-e61c-4917-9a1a-4a82e67e7f3c'),
       ('faa74e7e-797a-44f8-8c80-31c0f7964e78', 1, '8dda7272-e4dc-4826-b167-d5f31d6b6967'),
       ('faa74e7e-797a-44f8-8c80-31c0f7964e78', 1, 'fcbb60e9-ae9b-4343-992d-07fdca0d0719'),
       ('0792d0dd-044d-43df-8031-5f9377522502', 1, '6ae89214-da50-4ae3-ac4a-fe40b75e70e4'),
       ('0792d0dd-044d-43df-8031-5f9377522502', 1, '1e2f7f9c-a521-4597-b706-847bdfa0a135'),
       ('9affc3a2-6ad9-4677-ae6f-dcc8c77d878b', 1, '3453ee35-54af-4f07-aece-f0ee494d1b82'),
       ('9affc3a2-6ad9-4677-ae6f-dcc8c77d878b', 1, '4e9a4878-9ad5-487b-98b9-d308a256cbff'),
       ('9affc3a2-6ad9-4677-ae6f-dcc8c77d878b', 1, '96067fe3-eaa2-4cdb-b4bd-50f2c4b48add'),
       ('9affc3a2-6ad9-4677-ae6f-dcc8c77d878b', 1, '82e8b1b6-ad30-43bb-89b1-5201bfc8940a'),
       ('a37de399-5404-4afb-b722-07b790f49ecc', 1, '849d751b-bc0b-4980-b256-41f418fda81c'),
       ('a37de399-5404-4afb-b722-07b790f49ecc', 1, '7d52793c-3afc-450c-af5f-f5c1724044e4'),
       ('a37de399-5404-4afb-b722-07b790f49ecc', 1, '6e9b6a01-1a85-4dff-8e31-2c9049d333f8'),
       ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, '1dbd1d76-c93f-431f-ab33-0627c5f797b1'),
       ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, 'a48e0289-dc7d-43a2-a08e-34d0358f8d0d'),
       ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, '904515cb-15e0-4a4d-a7f1-10e187783dc0');

INSERT INTO event_organizer_favourite_services (id, static_service_id)
VALUES
    -- EVENT ORGANIZER FAVOURITE SERVICES
    ('b38d716b-4d2a-4fd3-b18c-bfa128f24b99', 'faa74e7e-797a-44f8-8c80-31c0f7964e78'),
    ('b38d716b-4d2a-4fd3-b18c-bfa128f24b99', '82828d99-3ed9-4a71-8c91-ecfe040411a5'),
    ('b38d716b-4d2a-4fd3-b18c-bfa128f24b99', '9447c5d5-3a82-44e2-8fe3-5d836f0eda63'),
    ('47c5fa7c-0d12-48e2-a4ed-9e4f441b383f', 'e30780b8-b7a8-4737-ae5e-cd11a9ad29fa'),
    ('47c5fa7c-0d12-48e2-a4ed-9e4f441b383f', '9affc3a2-6ad9-4677-ae6f-dcc8c77d878b'),
    ('47c5fa7c-0d12-48e2-a4ed-9e4f441b383f', '3ed7c6fd-155e-48a9-a376-52b9b3b43ad8'),
    ('9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0', '10c348f9-e96c-4073-8704-7012f2daa220'),
    ('9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0', '9affc3a2-6ad9-4677-ae6f-dcc8c77d878b'),
    ('9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0', 'faa74e7e-797a-44f8-8c80-31c0f7964e78'),
    ('1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236', '195eb1c5-6fd6-4139-a697-8cd906219525'),
    ('1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236', 'f501fea8-7903-4ff0-a3d3-49493282a69e'),
    ('1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236', '82828d99-3ed9-4a71-8c91-ecfe040411a5');

INSERT INTO listing_review (id, comment, grade, pending_status, event_organizer_id, product_static_product_id, service_static_service_id)
VALUES
    ('bf8b3ec5-0846-47b7-9f3d-e1b70f6a7311', 'The event catering was exceptional, the food was delicious and everyone enjoyed it.', 5, 'APPROVED', 'b38d716b-4d2a-4fd3-b18c-bfa128f24b99', '5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', null),
    ('4ac2e6fb-4f68-4f37-832d-689cc5f4584e', 'The DJ played great music all night! The vibe was amazing and everyone had a great time.', 5, 'APPROVED', '47c5fa7c-0d12-48e2-a4ed-9e4f441b383f', null, 'e30780b8-b7a8-4737-ae5e-cd11a9ad29fa'),
    ('2df3f27e-716e-4707-bf6d-2fe56a92a08c', 'I hired a photographer for my wedding and the photos turned out stunning. Highly recommend!', 5, 'APPROVED', '9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0', 'ee3c6987-dfd9-4375-930f-cf14c55126a7', null),
    ('aeb91c71-1392-47bc-974e-f4842cf3859b', 'The videography service was top-notch. The video captured all the best moments of our event.', 5, 'APPROVED', 'b38d716b-4d2a-4fd3-b18c-bfa128f24b99', null, '82828d99-3ed9-4a71-8c91-ecfe040411a5'),
    ('da3b58d7-13d5-4b52-a708-d1e0798a4e1d', 'The security team was professional and made sure everything went smoothly during the event. Very reliable.', 4, 'APPROVED', '47c5fa7c-0d12-48e2-a4ed-9e4f441b383f', '24a7cf0a-8a8b-46ad-bb23-f4c8be7b154d', null),
    ('b1577d06-6726-48e7-83b2-c7f2d388f07b', 'Transportation services were on time and comfortable. The drivers were polite and helpful.', 5, 'APPROVED', '9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0', null, '888f99a9-a0e5-469c-bc21-2d6bd429b777'),
    ('31a13d0e-cb59-47ea-9e79-e883bdbcd058', 'The venue was amazing and the staff was extremely helpful. Couldn’t have asked for a better place for our event.', 5, 'APPROVED', '47c5fa7c-0d12-48e2-a4ed-9e4f441b383f', '3c2ef35b-6e5d-429b-9bbf-62c9b7a4ab3b', null),
    ('47c07e3e-317b-4b2e-a5b3-b06d3a77f022', 'The catering options were diverse and the service was very friendly. The guests loved everything.', 4, 'APPROVED', 'b38d716b-4d2a-4fd3-b18c-bfa128f24b99', '327b9456-b9da-42e1-86c3-f4b25f38a0e9', null),
    ('1ec51b6a-408d-41b1-aeae-7a07fd25fce0', 'The event planner was so organized and helped us every step of the way. Highly recommend for anyone looking to organize a smooth event.', 5, 'APPROVED', '1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236', null, 'b008b01e-4f96-4233-873e-77617645c371'),
    ('c8a52907-dfdb-41f9-b6f4-2b8482d47d06', 'The band was fantastic. The live performance was energetic and really set the tone for the evening. Very happy with the service.', 5, 'APPROVED', '1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236', 'b42d2a1a-b118-49ea-b83a-b1aee1b67e2e', null);

INSERT INTO registration_attempt (id, time, profile_id)
VALUES
    ('f6bead0f-eaa7-4d50-87fc-12c3a22d8647', '2024-10-15T14:23:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4'),
    ('d18c76e0-0b3f-44f3-9520-6ea24c6742cd', '2024-10-18T09:12:45', '0d1f5f7a-6fcf-42d9-82fe-0090a35ea88c'),
    ('ad8f2c55-b9f7-43f9-bf61-82fc1d3c6d6c', '2024-10-20T16:45:30', '679eb520-7b8d-4c3a-b99f-720e6cfb759b'),
    ('1b4f8a2a-34b4-4268-90f9-72db84b54379', '2024-10-22T11:08:10', '4d7248cb-d5f2-4e9b-9eb2-b8ad1de05bcf'),
    ('0d18b7b9-988d-4647-b27e-1fd211cd1392', '2024-10-25T13:35:22', '27e8a1b2-4d23-4b6f-b5a5-79a76d6b758e'),
    ('8e3091c7-5588-47a9-9a5b-1d2b7bb5b10f', '2024-10-27T08:21:40', '542c3a1b-ffea-421d-b967-7d45968c6506'),
    ('5e52037f-b9de-4f58-a397-22a49e3d83d8', '2024-10-29T19:40:55', '05fdc5be-d59b-468e-8466-d951a4a8d457'),
    ('21f2417a-e670-4f2e-bb0a-bd5e33024d98', '2024-10-30T10:02:14', '03b88b74-0797-4f35-b15f-ff2a3c3e5c88'),
    ('5f4389ca-d5f2-4a1f-a4c8-e6609f2b1cf3', '2024-11-01T15:25:48', '70c7425e-c5f0-44f0-9e9d-44e4087fce62'),
    ('ea4b2417-d98f-4969-9fae-c44d1cf92944', '2024-11-03T12:11:30', '5a72482a-cd36-46e4-8c09-7b2ff1e22071');

INSERT INTO user_report (id, ban_start_date_time, report_date_time, status, from_profile_id, to_profile_id)
VALUES
    ('71adf9c5-cd0f-4639-bb99-2f8c5d1f0e65', null, '2024-11-05T10:30:00', 'DECLINED', '4d7248cb-d5f2-4e9b-9eb2-b8ad1de05bcf', '0cd13f4e-f7de-4533-9071-c42b7b3b4d45'),
    ('b02d9284-c6a4-44e6-9d73-bcd4fded42c9', '2024-11-10T09:15:00', '2024-11-07T16:45:00', 'APPROVED', '4d7248cb-d5f2-4e9b-9eb2-b8ad1de05bcf', '0cd13f4e-f7de-4533-9071-c42b7b3b4d45');

-- EVENTS

INSERT INTO event_type (id, description, is_active, name)
VALUES
    ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', 'A formal ceremony where two people are united in marriage, typically with a reception afterward.', true, 'Wedding'),
    ('cba94c6d-ef28-4de2-bbe7-0e1a7797d941', 'A high-energy electronic music party, often featuring DJs and vibrant light shows.', true, 'Techno Party'),
    ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', 'A formal gathering often held to celebrate a milestone or corporate achievement, usually involving speeches and entertainment.', true, 'Corporate Event'),
    ('2a3fbe6a-d495-4090-9e2e-09e2a4043460', 'A festive occasion to celebrate the birth of a child, typically with family and close friends.', true, 'Birthday Party'),
    ('15c1de85-50a4-4b60-a5c2-bb349d3173ab', 'A social event often hosted outdoors, where guests enjoy food, music, and dancing under the stars.', true, 'Outdoor Festival'),
    ('d1b237e6-d7a9-4797-b39f-9c2a1fcf93c0', 'An elegant celebration often associated with the holiday season, featuring gourmet food and dancing.', true, 'Gala Dinner'),
    ('17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', 'A casual or formal celebration of a couple’s engagement, often involving close friends and family.', true, 'Engagement Party'),
    ('33a8ecb0-81a5-44a0-b07d-028b209ef4fd', 'A themed event with costumes, music, and dance, typically celebrating Halloween or other occasions.', true, 'Costume Party'),
    ('b740de8f-7a23-4fbb-a6ae-5b0e7777cd18', 'A ceremony and celebration marking a major religious or cultural milestone in a person’s life, such as a baptism or bar mitzvah.', true, 'Religious Ceremony');

INSERT INTO event (id, name, description, address, city, date, time, guest_count, is_public, latitude, longitude, event_type_id, event_organizer_id)
VALUES
    ('ea0d1c1b-67fa-4f7e-b00d-78129d742d01', 'Smith Wedding', 'A beautiful wedding ceremony with a reception to follow.', 'Terazije 20', 'Beograd', '2025-05-15', '15:00', 150, true, 44.8150, 20.4606, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', 'b38d716b-4d2a-4fd3-b18c-bfa128f24b99'),
    ('2c9f1c4d-1cb5-48f2-8618-78e3be06f27f', 'Techno Beats Night', 'A vibrant night filled with music and lights.', 'Cvetni Trg 3', 'Beograd', '2025-06-20', '22:00', 300, true, 44.8075, 20.4677, 'cba94c6d-ef28-4de2-bbe7-0e1a7797d941', '9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0'),
    ('f00de5a9-8c76-4a87-b2cb-1c0b7bc4c9c7', 'Annual Gala 2025', 'A formal event celebrating corporate milestones.', 'Sindjelićeva 1', 'Novi Sad', '2025-09-10', '19:00', 500, false, 45.2528, 19.8422, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93', '47c5fa7c-0d12-48e2-a4ed-9e4f441b383f'),
    ('86ad4f02-5c5c-42e6-b789-3181fa81e8f7', 'Toms Birthday Bash', 'A lively party celebrating Toms big day!', 'Dunavska 15', 'Novi Sad', '2025-10-31', '18:30', 100, true, 45.2551, 19.8445, '2a3fbe6a-d495-4090-9e2e-09e2a4043460', '1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236'),
    ('672cde38-24b6-4c79-bd99-4f58c795ba21', 'Summer Outdoor Festival', 'An outdoor festival featuring live music and food trucks.', 'Niška Tvrdjava bb', 'Niš', '2025-08-15', '12:00', 2000, true, 43.3247, 21.9033, '15c1de85-50a4-4b60-a5c2-bb349d3173ab', 'b38d716b-4d2a-4fd3-b18c-bfa128f24b99'),
    ('92bb1a4e-98eb-4a63-ae1c-4195c19ae74e', 'Holiday Gala Dinner', 'An elegant dinner event to celebrate the holiday season.', 'Novosadskog Sajma 35', 'Novi Sad', '2025-12-20', '20:00', 200, false, 45.2452, 19.8368, 'd1b237e6-d7a9-4797-b39f-9c2a1fcf93c0', '9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0'),
    ('38c02a1d-e21c-45f3-a486-518a0071a8e9', 'Susans Engagement Party', 'A joyous celebration of Susans engagement.', 'Obrenovićeva 52', 'Niš', '2025-04-08', '16:00', 80, true, 43.3195, 21.8962, '17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', '47c5fa7c-0d12-48e2-a4ed-9e4f441b383f'),
    ('e7c6d6b5-dae4-4d59-a6c1-1233fa72f5e1', 'Halloween Costume Party', 'A spooky night of fun, costumes, and music.', 'Šanac 1', 'Novi Sad', '2025-10-31', '20:00', 250, true, 45.2517, 19.8614, '33a8ecb0-81a5-44a0-b07d-028b209ef4fd', '1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236'),
    ('e23ad6d4-cff6-41ec-bcab-d5674d875e2b', 'Community Barbecue and Fair', 'A family-friendly event with food, games, and entertainment.', 'Kalemegdan bb', 'Beograd', '2025-07-04', '10:00', 300, true, 44.8233, 20.4502, '15c1de85-50a4-4b60-a5c2-bb349d3173ab', 'b38d716b-4d2a-4fd3-b18c-bfa128f24b99'),
    ('a927e6d5-8e67-4f7f-a1cd-fb56c7d5e8e9', 'Traditional Religious Ceremony', 'A ceremonial event marking a significant cultural milestone.', 'Trg Slobode 4', 'Novi Sad', '2025-03-15', '11:00', 150, false, 45.2553, 19.8457, 'b740de8f-7a23-4fbb-a6ae-5b0e7777cd18', '47c5fa7c-0d12-48e2-a4ed-9e4f441b383f'),
    ('789de567-1234-4f67-8901-3456789abcd1', 'Winter Wonderland Festival', 'A cozy winter event with warm drinks and music.', 'Medijana bb', 'Niš', '2025-12-10', '14:00', 500, true, 43.3196, 21.9001, '15c1de85-50a4-4b60-a5c2-bb349d3173ab', '9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0'),
    ('890de567-4321-5f67-1234-4567890abcde', 'Spring Art Expo', 'An art exhibition showcasing local talent.', 'Kralja Milana 50', 'Niš', '2025-03-30', '10:00', 100, false, 43.3198, 21.8956, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93', '47c5fa7c-0d12-48e2-a4ed-9e4f441b383f'),
    ('901de678-9876-6f78-3456-5678901bcdef', 'Jazz Night Out', 'A night of soulful jazz and fine dining.', 'Knez Mihailova 56', 'Beograd', '2025-06-25', '19:30', 150, true, 44.8172, 20.4575, 'd1b237e6-d7a9-4797-b39f-9c2a1fcf93c0', 'b38d716b-4d2a-4fd3-b18c-bfa128f24b99'),
    ('5678a9bc-12de-4f56-b78a-3456c78def90', 'Kids Science Fair', 'A fun and educational event for kids.', 'Nemanjina 6', 'Beograd', '2025-09-20', '10:00', 300, true, 44.8040, 20.4651, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93', '1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236'),
    ('6789a1de-34bc-5f67-c890-4567e89def12', 'Retro Music Night', 'A nostalgic night of music from the 80s and 90s.', 'Laze Telečkog 7', 'Niš', '2025-10-05', '21:00', 200, true, 43.3201, 21.8932, 'cba94c6d-ef28-4de2-bbe7-0e1a7797d941', '9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0'),
    ('7890b2de-45cd-6f78-d901-5678f90ef123', 'Family Movie Night', 'An outdoor movie experience for families.', 'Tašmajdan Park', 'Beograd', '2025-08-25', '19:00', 250, true, 44.8089, 20.4701, '15c1de85-50a4-4b60-a5c2-bb349d3173ab', 'b38d716b-4d2a-4fd3-b18c-bfa128f24b99'),
    ('8901c3ef-56de-7f89-e012-6789f01f2345', 'Craft Beer Festival', 'A festival celebrating local craft beers.', 'Futoška 18', 'Novi Sad', '2025-06-10', '15:00', 400, true, 45.2486, 19.8419, '15c1de85-50a4-4b60-a5c2-bb349d3173ab', '47c5fa7c-0d12-48e2-a4ed-9e4f441b383f'),
    ('9012d4f0-67ef-8f90-f123-7890a12f3456', 'Meditation Workshop', 'A relaxing session for mindfulness and meditation.', 'Kralja Petra 75', 'Niš', '2025-07-18', '09:00', 50, false, 43.3178, 21.8965, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93', '1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236'),
    ('a12b3c4d-5678-9abc-def0-123456789abc', 'Spring Carnival', 'A family-friendly carnival with games and food.', 'Skadarlija 27', 'Beograd', '2025-04-10', '10:00', 300, true, 44.8186, 20.4689, '15c1de85-50a4-4b60-a5c2-bb349d3173ab', 'b38d716b-4d2a-4fd3-b18c-bfa128f24b99'),
    ('b23c4d5e-6789-abcd-ef01-23456789abcd', 'Wine Tasting Night', 'An evening exploring fine wines.', 'Zmaj Jovina 7', 'Novi Sad', '2025-06-15', '18:00', 50, false, 45.2561, 19.8462, '15c1de85-50a4-4b60-a5c2-bb349d3173ab', '9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0'),
    ('c34d5e6f-789a-bcde-f012-3456789abcde', 'Charity Fun Run', 'A community event to raise funds for a local charity.', 'Bulevar Kralja Aleksandra 84', 'Beograd', '2025-05-20', '09:00', 400, true, 44.7982, 20.4775, 'b740de8f-7a23-4fbb-a6ae-5b0e7777cd18', '47c5fa7c-0d12-48e2-a4ed-9e4f441b383f'),
    ('d45e6f7a-89ab-cdef-0123-456789abcdef', 'Kids Art Workshop', 'A creative event for children.', 'Trg Mladenaca 5', 'Novi Sad', '2025-07-05', '14:00', 30, true, 45.2548, 19.8436, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93', '1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236'),
    ('e56f7b8e-9abc-def0-1234-56789abcdefd', 'Beograd Jazz Festival', 'A night of incredible jazz performances.', 'Resavska 28', 'Niš', '2025-06-25', '20:00', 600, true, 43.3210, 21.8945, 'cba94c6d-ef28-4de2-bbe7-0e1a7797d941', 'b38d716b-4d2a-4fd3-b18c-bfa128f24b99'),
    ('f67a8396-abcd-ef01-2345-6789abcdefbc', 'Cultural Heritage Day', 'A celebration of local history and traditions.', 'Petrovaradin Fortress', 'Novi Sad', '2025-08-18', '11:00', 150, true, 45.2516, 19.8619, 'b740de8f-7a23-4fbb-a6ae-5b0e7777cd18', '9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0'),
    ('f7819b0a-bcde-f012-3456-789abcdeffec', 'Outdoor Movie Screening', 'A family-friendly outdoor movie night.', 'Čair Park', 'Niš', '2025-07-12', '19:30', 200, true, 43.3189, 21.8983, '15c1de85-50a4-4b60-a5c2-bb349d3173ab', '1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236'),
    ('a89e0bdc-cdef-0123-4567-89abcdef2345', 'Startup Pitch Night', 'An event showcasing innovative startups.', 'Bulevar Dr Zorana Đinđića 10a', 'Niš', '2025-09-01', '18:00', 100, false, 43.3165, 21.8912, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93', '47c5fa7c-0d12-48e2-a4ed-9e4f441b383f'),
    ('49021325-def0-1234-5678-9abcdef12453', 'Fitness Bootcamp', 'An energetic outdoor fitness event.', 'Ada Ciganlija', 'Beograd', '2025-05-05', '07:00', 80, true, 44.7866, 20.4489, '15c1de85-50a4-4b60-a5c2-bb349d3173ab', 'b38d716b-4d2a-4fd3-b18c-bfa128f24b99'),
    ('ab349894-9101-4157-b71b-40341c08fed2', 'Niš Tech Expo', 'An exhibition showcasing the latest in technology.', 'Bulevar Mediana 42', 'Niš', '2025-04-22', '10:00', 300, true, 43.3231, 21.8905, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93', '1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236'),
    ('57787b97-680f-436f-95e4-9690357472e4', 'Niš Jazz Nights', 'A weekend of smooth jazz performances.', 'Kopitareva 5', 'Niš', '2025-07-15', '20:00', 150, true, 43.3218, 21.8974, 'cba94c6d-ef28-4de2-bbe7-0e1a7797d941', 'b38d716b-4d2a-4fd3-b18c-bfa128f24b99'),
    ('f10140e9-3a09-42fa-8522-a702d70a1c77', 'Cultural Heritage Walk', 'A guided tour through historic sites.', 'Kopitareva 5', 'Niš', '2025-06-05', '09:00', 100, true, 43.3209, 21.8958, 'b740de8f-7a23-4fbb-a6ae-5b0e7777cd18', '9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0'),
    ('b10994d1-224f-4f6d-bdd7-86f7fde9df5b', 'Belgrade Marathon', 'An annual marathon through the heart of the city.', 'Skadarlija 27', 'Beograd', '2025-05-10', '07:00', 500, true, 44.8176, 20.4569, 'b740de8f-7a23-4fbb-a6ae-5b0e7777cd18', '1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236'),
    ('56f3c073-2fbc-463d-b3a2-bea9dc536454', 'Startup Meetup', 'An event for entrepreneurs and investors.', 'Trg Mladenaca 5', 'Novi Sad', '2025-09-20', '16:00', 200, true, 45.2671, 19.8335, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93', '47c5fa7c-0d12-48e2-a4ed-9e4f441b383f'),
    ('9cafdd5d-3099-4591-9187-8d7fa43e34c8', 'Niš Food Festival', 'A celebration of local and international cuisine.', 'Kopitareva 5', 'Niš', '2025-08-12', '11:00', 300, true, 43.3209, 21.8958, '15c1de85-50a4-4b60-a5c2-bb349d3173ab', '9c88f9ab-c2c9-4823-bf51-e3e263dcd5b0'),
    ('f6a6fb8b-680b-4a7c-ade2-7185c3b3c280', 'Art in the Park', 'An open-air exhibition of local art.', 'Trg Mladenaca 5', 'Novi Sad', '2025-06-30', '12:00', 200, true, 45.2671, 19.8335, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93', '47c5fa7c-0d12-48e2-a4ed-9e4f441b383f'),
    ('bcc16eba-53df-42e9-b3cd-5d41bf581d94', 'Book Fair 2025', 'An annual book fair with author signings.', 'Skadarlija 27', 'Beograd', '2025-10-10', '10:00', 400, true, 44.8176, 20.4569, 'b740de8f-7a23-4fbb-a6ae-5b0e7777cd18', '1d832a6e-7b3f-4cd4-bc37-fac3e0ef9236'),
    ('379c96eb-7391-48b4-adc3-f07095576d3b', 'Tech Career Fair', 'Connecting job seekers with tech companies.', 'Kopitareva 5', 'Niš', '2025-11-05', '09:00', 250, true, 43.3209, 21.8958, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93', 'b38d716b-4d2a-4fd3-b18c-bfa128f24b99');


INSERT INTO event_activity (id, name, description, start_time, end_time, location, event_id)
VALUES
    ('c68e6b75-31a3-4f9c-a2ec-5f15246ad2a1', 'Ceremony', 'Wedding ceremony in the garden', '15:00', '16:00', 'Garden Venue', NULL),
    ('ae2c6d3f-8ff7-41ec-b1be-1fa0931c3902', 'Reception', 'Dinner and dancing with guests', '16:30', '20:00', 'Banquet Hall', NULL),

    ('bfa9d7fc-11c6-4e83-8615-6a4e39d10c44', 'Opening DJ Set', 'Opening techno beats by local DJs', '22:00', '23:30', 'Main Stage', NULL),
    ('f729e5ef-99f5-4664-90db-7e31eebbf250', 'Headliner Performance', 'Top DJ performance with visuals', '23:45', '02:00', 'Main Stage', NULL),

    ('ed8a78c7-89f0-4c15-b865-58fc8132b221', 'Welcome Speech', 'Opening remarks by CEO', '19:00', '19:30', 'Grand Ballroom', NULL),
    ('7d89b90f-b8cd-47c7-a1b3-5566cbf3a5d6', 'Dinner & Awards', 'Formal dinner with award ceremony', '19:30', '22:00', 'Banquet Hall', NULL),

    ('e538d6d4-7881-4b20-8717-5b35f4bfa8fc', 'Games & Drinks', 'Interactive games and open bar', '18:30', '20:00', 'Terrace', NULL),
    ('06d4748f-bf3b-4a6f-93b2-58e4d19b7f45', 'Dance Party', 'Birthday celebration and dancing', '20:00', '23:00', 'Dance Floor', NULL);
--GUEST LISTS

INSERT INTO guest_invited_events (id, event_id) VALUES
                                                    ('634182f1-9a18-433b-82d8-dad5aa4069f8', 'ea0d1c1b-67fa-4f7e-b00d-78129d742d01'),
                                                    ('634182f1-9a18-433b-82d8-dad5aa4069f8', 'f00de5a9-8c76-4a87-b2cb-1c0b7bc4c9c7'),
                                                    ('c633e080-fad0-4195-8a52-688c149700a1', 'ea0d1c1b-67fa-4f7e-b00d-78129d742d01'),
                                                    ('c633e080-fad0-4195-8a52-688c149700a1', 'f00de5a9-8c76-4a87-b2cb-1c0b7bc4c9c7'),
                                                    ('95a4669b-9ee6-4608-a4d1-ae52da25be36', 'ea0d1c1b-67fa-4f7e-b00d-78129d742d01'),
                                                    ('95a4669b-9ee6-4608-a4d1-ae52da25be36', 'f00de5a9-8c76-4a87-b2cb-1c0b7bc4c9c7');

INSERT INTO guest_accepted_events (id, event_id) VALUES
                                                     ('0f0e83c6-6764-4c27-bca6-7369aea6acaa', '2c9f1c4d-1cb5-48f2-8618-78e3be06f27f'),
                                                     ('0f0e83c6-6764-4c27-bca6-7369aea6acaa', 'f00de5a9-8c76-4a87-b2cb-1c0b7bc4c9c7'),
                                                     ('4b423147-32cf-4a90-9238-a3a5934aaee9', '2c9f1c4d-1cb5-48f2-8618-78e3be06f27f'),
                                                     ('4b423147-32cf-4a90-9238-a3a5934aaee9', 'f00de5a9-8c76-4a87-b2cb-1c0b7bc4c9c7');


INSERT INTO product_budget_item(id, max_price, versioned_product_static_product_id, versioned_product_version, product_category_id, event_id)
VALUES
    ('9f1fed25-b54b-4322-b206-341c9e2daa47', 100, null, null, 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db', 'ea0d1c1b-67fa-4f7e-b00d-78129d742d01'),
    ('e3565a02-8603-4ed7-b207-467b6f4d2120', 500, '5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b', 'ea0d1c1b-67fa-4f7e-b00d-78129d742d01');

INSERT INTO service_budget_item(id, end_time, max_price, start_time, versioned_service_static_service_id, versioned_service_version, service_category_id, event_id)
VALUES
    ('be10be60-9ab2-46a6-acbf-a310b018cdfa', null, 100, null, null, null, 'd46e1f95-8a90-4745-8000-629f412bdbab', 'ea0d1c1b-67fa-4f7e-b00d-78129d742d01'),
    ('89d39b80-997d-4ffe-ba9d-74fc4e6c0e0b', '2025-05-15 16:30:00', 500, '2025-05-15 15:30:00', 'daa22294-5377-487a-aa3f-7cd5a42cc568', 1, 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3', 'ea0d1c1b-67fa-4f7e-b00d-78129d742d01');

INSERT INTO event_images (event_id, images)
VALUES
    ('ea0d1c1b-67fa-4f7e-b00d-78129d742d01', 'ae688d2f-8f95-47ba-8d43-33f5621a1d26'),
    ('ea0d1c1b-67fa-4f7e-b00d-78129d742d01', 'a6df4b50-77b4-41bc-aceb-ab4d442d961b'),
    ('2c9f1c4d-1cb5-48f2-8618-78e3be06f27f', 'd6b73c11-c93d-459d-b050-9cc9a32935c0'),
    ('2c9f1c4d-1cb5-48f2-8618-78e3be06f27f', '00e07893-4f94-4796-a8e1-4f3496056200'),
    ('2c9f1c4d-1cb5-48f2-8618-78e3be06f27f', 'd17c244c-c917-4791-9a4d-0b09a880ee16'),
    ('f00de5a9-8c76-4a87-b2cb-1c0b7bc4c9c7', '3da737d5-5152-41f3-a0df-e5eb42c5a2bd'),
    ('f00de5a9-8c76-4a87-b2cb-1c0b7bc4c9c7', 'deb0a1ad-d4f6-4164-b1af-84ee854f8e6c'),
    ('86ad4f02-5c5c-42e6-b789-3181fa81e8f7', '0c5501a3-17ad-4583-97dd-dc58b2d6f1f0'),
    ('86ad4f02-5c5c-42e6-b789-3181fa81e8f7', '1f918cbb-a7e2-42c3-9e31-281e24b5836c'),
    ('86ad4f02-5c5c-42e6-b789-3181fa81e8f7', '050e14f5-79d9-4b9a-8eed-3d77dc1cafb4'),
    ('86ad4f02-5c5c-42e6-b789-3181fa81e8f7', '87120356-8b33-4556-b672-9626983cca00'),
    ('672cde38-24b6-4c79-bd99-4f58c795ba21', 'b8c3d867-ce18-44a2-af2c-f5e390db3606'),
    ('672cde38-24b6-4c79-bd99-4f58c795ba21', '417dfb1c-6fcb-4341-94ce-8437c61bc683'),
    ('672cde38-24b6-4c79-bd99-4f58c795ba21', '20a48de0-28c3-42af-a501-687bed55f3f1'),
    ('92bb1a4e-98eb-4a63-ae1c-4195c19ae74e', '442f369c-5446-4661-8b37-b93e4e10e62c'),
    ('92bb1a4e-98eb-4a63-ae1c-4195c19ae74e', '48108219-b50d-47a3-87bd-504e36ff8ebe'),
    ('38c02a1d-e21c-45f3-a486-518a0071a8e9', '4f30f1c4-56fe-4407-87c6-ca5f53847121'),
    ('38c02a1d-e21c-45f3-a486-518a0071a8e9', '169b829c-df27-4c44-974f-983253b275d2'),
    ('38c02a1d-e21c-45f3-a486-518a0071a8e9', 'aac25a6d-0562-4352-b977-facebb5ca4e0'),
    ('38c02a1d-e21c-45f3-a486-518a0071a8e9', 'bcc9890d-8903-4e72-93cd-9aef5c80fcce'),
    ('e7c6d6b5-dae4-4d59-a6c1-1233fa72f5e1', '5fade9e0-6c79-4fa7-80c9-eb00b01f1f60'),
    ('e7c6d6b5-dae4-4d59-a6c1-1233fa72f5e1', '4c04d679-674e-4ce3-b699-f8b523660623'),
    ('e7c6d6b5-dae4-4d59-a6c1-1233fa72f5e1', '56999f10-7155-4a91-8117-83a29ea3ea93'),
    ('e7c6d6b5-dae4-4d59-a6c1-1233fa72f5e1', 'bed96cc8-61cc-4bd5-809f-48c81fd6774a'),
    ('e23ad6d4-cff6-41ec-bcab-d5674d875e2b', '7dd404e7-a7e1-4625-8048-c65a41704ac5'),
    ('e23ad6d4-cff6-41ec-bcab-d5674d875e2b', 'd8aa475c-149b-47cb-823a-ea754686e88d'),
    ('a927e6d5-8e67-4f7f-a1cd-fb56c7d5e8e9', '4a3e74d7-24e4-41cc-98a9-d69c90ca27b1'),
    ('a927e6d5-8e67-4f7f-a1cd-fb56c7d5e8e9', '7518897e-6988-476d-ac45-767786f6990b'),
    ('789de567-1234-4f67-8901-3456789abcd1', '0016d401-886f-4abd-b864-758c2dde9b60'),
    ('789de567-1234-4f67-8901-3456789abcd1', 'f946f667-c503-4954-8051-1194df99ed7e'),
    ('890de567-4321-5f67-1234-4567890abcde', 'b1f665ac-6a9a-4554-86e6-3ebfc474b4c4'),
    ('890de567-4321-5f67-1234-4567890abcde', '77d3a811-4113-4b6c-a8eb-aeb0efd35ff5'),
    ('890de567-4321-5f67-1234-4567890abcde', '648c2b95-cdf8-4e2b-808c-964d5cad0a2e'),
    ('890de567-4321-5f67-1234-4567890abcde', '83627192-1f0b-44fa-98d1-97ade6dbb80f'),
    ('901de678-9876-6f78-3456-5678901bcdef', '86f6c7fd-d2ea-4f49-a09f-dd2b5c0797b9'),
    ('901de678-9876-6f78-3456-5678901bcdef', 'e4cd69c4-6fc5-4ddb-9551-85943339eb55'),
    ('901de678-9876-6f78-3456-5678901bcdef', 'f513ec28-c0c1-4324-9a37-261c6e70d16c'),
    ('5678a9bc-12de-4f56-b78a-3456c78def90', 'f05c4ba7-d199-4fa1-9e25-2cac9fa9518d'),
    ('5678a9bc-12de-4f56-b78a-3456c78def90', 'ac5fd790-bf3b-487f-b545-2228a53fa33d'),
    ('5678a9bc-12de-4f56-b78a-3456c78def90', 'a3ea23ff-70e6-47b0-83a7-439e4f96f25c'),
    ('5678a9bc-12de-4f56-b78a-3456c78def90', '10225c23-880b-45f8-8801-62aadcb89920'),
    ('6789a1de-34bc-5f67-c890-4567e89def12', '97a23360-7afc-4187-b2b1-e5e9f4d8e3bf'),
    ('6789a1de-34bc-5f67-c890-4567e89def12', '4b758ff9-9c3b-463d-9bad-02762e726fe2'),
    ('6789a1de-34bc-5f67-c890-4567e89def12', 'd747d1e2-960e-4932-affe-47de1c0fc57f'),
    ('6789a1de-34bc-5f67-c890-4567e89def12', '51796753-c2a1-49b0-ab52-11b62b505ee4'),
    ('7890b2de-45cd-6f78-d901-5678f90ef123', '195a3457-d2a6-4107-b531-ee7f7379bd78'),
    ('7890b2de-45cd-6f78-d901-5678f90ef123', '0c883c68-e5b0-4a89-aefe-0d97a706bfc4'),
    ('7890b2de-45cd-6f78-d901-5678f90ef123', 'aaf38f99-9f2b-4f6c-87cf-f5893ae4163b'),
    ('7890b2de-45cd-6f78-d901-5678f90ef123', '2c196040-ea14-4e27-be35-d6491cddc94b'),
    ('8901c3ef-56de-7f89-e012-6789f01f2345', '020cb2be-f87f-4522-a42f-117526c4fee9'),
    ('8901c3ef-56de-7f89-e012-6789f01f2345', '3c875236-6042-43d3-9a82-944c07ddc7b7'),
    ('8901c3ef-56de-7f89-e012-6789f01f2345', '9ec780fa-b55d-42be-b0b0-36d3f4a23fe0'),
    ('9012d4f0-67ef-8f90-f123-7890a12f3456', '98c5cd1b-fa2f-4f91-8931-2368a5c6448b'),
    ('9012d4f0-67ef-8f90-f123-7890a12f3456', '136596b5-0c87-4f6f-8e97-5c03df5fb5cc'),
    ('9012d4f0-67ef-8f90-f123-7890a12f3456', '44e0f2f2-13b9-449b-954c-1b7188026c73'),
    ('a12b3c4d-5678-9abc-def0-123456789abc', '23730253-dbaf-4016-a7d7-097c475a1d97'),
    ('a12b3c4d-5678-9abc-def0-123456789abc', '2b974b21-340d-4203-a167-31ebb6ff314a'),
    ('a12b3c4d-5678-9abc-def0-123456789abc', 'c15277bf-57c0-4362-8988-0691e1e514b1'),
    ('b23c4d5e-6789-abcd-ef01-23456789abcd', 'ba34dae1-0fcb-438f-ab7f-b0f058e5e052'),
    ('b23c4d5e-6789-abcd-ef01-23456789abcd', '67239f70-b0dd-48a1-a18c-9b819d8bb6cd'),
    ('b23c4d5e-6789-abcd-ef01-23456789abcd', '5e378245-31f9-4ba3-8f32-d8be5e5c2e35'),
    ('b23c4d5e-6789-abcd-ef01-23456789abcd', 'd98afa3c-077a-4781-9d34-7e879f0be1a4'),
    ('c34d5e6f-789a-bcde-f012-3456789abcde', '9040fa5c-5b70-4f98-bc89-23c2541f4849'),
    ('c34d5e6f-789a-bcde-f012-3456789abcde', 'ce0e0779-19e6-4370-9481-a296f6f3ec0f'),
    ('c34d5e6f-789a-bcde-f012-3456789abcde', 'ddad4ad6-9faf-45ff-8b4b-add49cc77697'),
    ('d45e6f7a-89ab-cdef-0123-456789abcdef', 'cc8aa175-fd98-473b-ae31-6227330bf75f'),
    ('d45e6f7a-89ab-cdef-0123-456789abcdef', '16011810-d2db-4e7a-94b0-3bb68314d7e5'),
    ('d45e6f7a-89ab-cdef-0123-456789abcdef', '589d6239-f267-4117-b9ae-2e650a1be659'),
    ('d45e6f7a-89ab-cdef-0123-456789abcdef', '6514538c-4caa-49e2-8761-538562695e82'),
    ('e56f7b8e-9abc-def0-1234-56789abcdefd', '19c22798-22d4-4b9f-b2ad-9c963af4e020'),
    ('e56f7b8e-9abc-def0-1234-56789abcdefd', '120b2b72-dc81-4dd7-a191-bc9c4b4ad01f'),
    ('e56f7b8e-9abc-def0-1234-56789abcdefd', 'f0763505-5f24-48b0-8c39-9e6381ad64fc'),
    ('e56f7b8e-9abc-def0-1234-56789abcdefd', '280654b8-60d1-4b0c-8eb5-5922df670b91'),
    ('f67a8396-abcd-ef01-2345-6789abcdefbc', '22d6cb3a-2992-423b-b81e-fcbf1382c771'),
    ('f67a8396-abcd-ef01-2345-6789abcdefbc', '6e000bb2-18d6-441c-b2af-53e36f0f1198'),
    ('f67a8396-abcd-ef01-2345-6789abcdefbc', '57b736cf-298b-4382-9bdd-82d6d0ab6ba9'),
    ('f67a8396-abcd-ef01-2345-6789abcdefbc', 'f758ad13-5438-4af9-93d7-d618baa96e05'),
    ('f7819b0a-bcde-f012-3456-789abcdeffec', '5694f337-27d5-4a57-a650-213f744e51e7'),
    ('f7819b0a-bcde-f012-3456-789abcdeffec', '35b4c904-1b7d-4d37-bdf3-b952413a0225'),
    ('f7819b0a-bcde-f012-3456-789abcdeffec', '5a3da2c7-ccb8-496e-89a7-edf85ffd670f'),
    ('a89e0bdc-cdef-0123-4567-89abcdef2345', 'dc09e76e-ff8d-4db9-901c-cf588c65fc42'),
    ('a89e0bdc-cdef-0123-4567-89abcdef2345', 'e8dadf1f-1feb-47e1-a591-7c428bbbe665'),
    ('a89e0bdc-cdef-0123-4567-89abcdef2345', '8770c27b-c8a3-4f06-bfd1-89da095408ad'),
    ('a89e0bdc-cdef-0123-4567-89abcdef2345', '69d4c4d8-83f0-40f0-94ca-2a2277e18aeb'),
    ('49021325-def0-1234-5678-9abcdef12453', 'b4c6b822-94b4-425c-adfc-d9a513c29bf9'),
    ('49021325-def0-1234-5678-9abcdef12453', '7dda2069-05af-4df4-966e-3299c29b1617'),
    ('ab349894-9101-4157-b71b-40341c08fed2', '9c1969a5-25cf-4b1d-b3f6-341cebbf5bc8'),
    ('ab349894-9101-4157-b71b-40341c08fed2', '961eabae-0afb-4ae9-8f0c-5541fc3134d0'),
    ('ab349894-9101-4157-b71b-40341c08fed2', '48583ebb-0c99-4b52-9eb1-5b8289d18f7d'),
    ('57787b97-680f-436f-95e4-9690357472e4', 'c5758faf-d095-423c-b119-2ab04e218cef'),
    ('57787b97-680f-436f-95e4-9690357472e4', '5563d582-4efd-4323-8a7b-60bff51cf71f'),
    ('57787b97-680f-436f-95e4-9690357472e4', '566151c8-6c78-47dc-bb38-eb04207a3d5c'),
    ('57787b97-680f-436f-95e4-9690357472e4', '13c580c3-4613-4ec0-af1b-8c3d519cc20c'),
    ('f10140e9-3a09-42fa-8522-a702d70a1c77', 'cb4ae17b-25c6-4fe6-a21f-32b307526554'),
    ('f10140e9-3a09-42fa-8522-a702d70a1c77', 'beca22e5-8dda-446b-bbeb-d6fa120d37f7'),
    ('f10140e9-3a09-42fa-8522-a702d70a1c77', 'ff5dc931-4dda-43a0-8069-db62b83f38ee'),
    ('b10994d1-224f-4f6d-bdd7-86f7fde9df5b', '10f78107-95d6-49d2-a211-778d20980782'),
    ('b10994d1-224f-4f6d-bdd7-86f7fde9df5b', '0b05495b-cf53-483d-9a51-908f253de490'),
    ('b10994d1-224f-4f6d-bdd7-86f7fde9df5b', 'e66ecaf8-d37a-450e-8fe0-a1e7696c60ee'),
    ('56f3c073-2fbc-463d-b3a2-bea9dc536454', '8a70f195-ccba-42a1-b69e-0a5328c82a12'),
    ('56f3c073-2fbc-463d-b3a2-bea9dc536454', '0679f885-1137-4b6a-83d6-b0ec7518635b'),
    ('9cafdd5d-3099-4591-9187-8d7fa43e34c8', '70dcb2d1-10f5-4777-b227-eab5fff9977a'),
    ('9cafdd5d-3099-4591-9187-8d7fa43e34c8', '5078be0f-9e78-453e-9672-07264bdf9c4c'),
    ('f6a6fb8b-680b-4a7c-ade2-7185c3b3c280', '8b938fcd-8a8c-432c-ba49-db6ae9e3a71d'),
    ('f6a6fb8b-680b-4a7c-ade2-7185c3b3c280', '6a17c5ed-f351-41c8-bf46-867f02881c9c'),
    ('f6a6fb8b-680b-4a7c-ade2-7185c3b3c280', '1945c5ee-3896-4d4f-9c97-6a6c01edbe71'),
    ('f6a6fb8b-680b-4a7c-ade2-7185c3b3c280', '8ed07f9b-2c02-40b1-a426-d3006b107b7f'),
    ('bcc16eba-53df-42e9-b3cd-5d41bf581d94', 'd66413bc-12a7-4f34-b1b5-0d19f39cba2c'),
    ('bcc16eba-53df-42e9-b3cd-5d41bf581d94', '32b5fb4a-8950-447f-8ff7-e6e8000a7eb7'),
    ('bcc16eba-53df-42e9-b3cd-5d41bf581d94', '160edede-03db-4a08-9652-2e05c65f77ae'),
    ('bcc16eba-53df-42e9-b3cd-5d41bf581d94', 'a4db3ed3-2430-49af-aaed-0181c85abd63'),
    ('379c96eb-7391-48b4-adc3-f07095576d3b', '80af2cad-f6fd-4a47-8cec-0269451a7491'),
    ('379c96eb-7391-48b4-adc3-f07095576d3b', '083c65e2-d557-4f5e-a899-6cc243e86fcf'),
    ('379c96eb-7391-48b4-adc3-f07095576d3b', '7256d04b-d111-4d1f-87cd-1f3009c20942'),
    ('379c96eb-7391-48b4-adc3-f07095576d3b', 'e350ee23-e8fa-44dd-8304-ce1c84141efa');

-- RECOMMENDED CATEGORIES OF PRODUCTS PER EVENT TYPE

INSERT INTO eventtype_productcategory (eventtype_id, productcategory_id)
VALUES
    -- WEDDING
    ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'), -- FOOD
    ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b'), -- FIREWORKS
    ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'), -- DRINKS
    ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7'), -- DECORATIONS

    -- TECHNO PARTY
    ('cba94c6d-ef28-4de2-bbe7-0e1a7797d941', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b'), -- FIREWORKS
    ('cba94c6d-ef28-4de2-bbe7-0e1a7797d941', '1e3939e7-6822-432a-b322-0ab107f8d582'), -- LIGHTNING
    ('cba94c6d-ef28-4de2-bbe7-0e1a7797d941', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'), -- DRINKS
    ('cba94c6d-ef28-4de2-bbe7-0e1a7797d941', '72ecfc89-6745-4f5b-a7f2-97fd9ad3f890'), -- STAGE & TENTS

    -- CORPORATE EVENT
    ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'), -- FOOD
    ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', '1e3939e7-6822-432a-b322-0ab107f8d582'), -- LIGHTNING
    ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'), -- DRINKS
    ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', '72ecfc89-6745-4f5b-a7f2-97fd9ad3f890'), -- STAGE & TENTS

    -- BIRTHDAY PARTY
    ('2a3fbe6a-d495-4090-9e2e-09e2a4043460', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'), -- FOOD
    ('2a3fbe6a-d495-4090-9e2e-09e2a4043460', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b'), -- FIREWORKS
    ('2a3fbe6a-d495-4090-9e2e-09e2a4043460', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'), -- DRINKS
    ('2a3fbe6a-d495-4090-9e2e-09e2a4043460', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7'), -- DECORATIONS

    -- OUTDOOR FESTIVAL
    ('15c1de85-50a4-4b60-a5c2-bb349d3173ab', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'), -- FOOD
    ('15c1de85-50a4-4b60-a5c2-bb349d3173ab', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b'), -- FIREWORKS
    ('15c1de85-50a4-4b60-a5c2-bb349d3173ab', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'), -- DRINKS

    -- GALA DINNER
    ('d1b237e6-d7a9-4797-b39f-9c2a1fcf93c0', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'), -- FOOD
    ('d1b237e6-d7a9-4797-b39f-9c2a1fcf93c0', '1e3939e7-6822-432a-b322-0ab107f8d582'), -- LIGHTNING
    ('d1b237e6-d7a9-4797-b39f-9c2a1fcf93c0', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'), -- DRINKS
    ('d1b237e6-d7a9-4797-b39f-9c2a1fcf93c0', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7'), -- DECORATIONS

    -- ENGAGEMENT PARTY
    ('17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'), -- FOOD
    ('17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b'), -- FIREWORKS
    ('17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'), -- DRINKS
    ('17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7'), -- DECORATIONS

    -- COSTUME PARTY
    ('33a8ecb0-81a5-44a0-b07d-028b209ef4fd', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'), -- FOOD
    ('33a8ecb0-81a5-44a0-b07d-028b209ef4fd', 'fa8e6d4f-3f57-45d9-b44f-bc0b7580c82b'), -- FIREWORKS
    ('33a8ecb0-81a5-44a0-b07d-028b209ef4fd', '1e3939e7-6822-432a-b322-0ab107f8d582'), -- LIGHTNING
    ('33a8ecb0-81a5-44a0-b07d-028b209ef4fd', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'), -- DRINKS
    ('33a8ecb0-81a5-44a0-b07d-028b209ef4fd', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7'), -- DECORATIONS

    -- RELIGIOUS CEREMONY
    ('b740de8f-7a23-4fbb-a6ae-5b0e7777cd18', 'ae2a31d5-c7d1-4c30-b268-82d129edb3f6'), -- FOOD
    ('b740de8f-7a23-4fbb-a6ae-5b0e7777cd18', 'd13a78bc-9256-4e7f-90b5-354e3f7ab5db'), -- DRINKS
    ('b740de8f-7a23-4fbb-a6ae-5b0e7777cd18', 'f3b6fdeb-c684-47ac-b0be-b4173f36d3b7'); -- DECORATIONS

-- RECOMMENDED CATEGORIES OF SERVICES PER EVENT TYPE

INSERT INTO eventtype_servicecategory (eventtype_id, servicecategory_id)
VALUES
    -- WEDDING
    ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'), -- MUSIC
    ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', 'd46e1f95-8a90-4745-8000-629f412bdbab'), -- CATERING
    ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', '6b351a75-3061-4d96-8856-d58f1576a568'), -- PHOTOGRAPHY
    ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', '3d5cb7b1-e512-4eae-bcd9-c2954b643b1b'), -- VIDEOGRAPHY
    ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', 'f9c3bc34-6316-47a1-b61a-85f842f8a76d'), -- GUEST TRANSPORTATION
    ('a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832', '3d0107f7-2cfa-4e95-b5b1-0136034602b6'), -- EVENT SECURITY

    -- TECHNO PARTY
    ('cba94c6d-ef28-4de2-bbe7-0e1a7797d941', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'), -- MUSIC
    ('cba94c6d-ef28-4de2-bbe7-0e1a7797d941', '3d0107f7-2cfa-4e95-b5b1-0136034602b6'), -- EVENT SECURITY

    -- CORPORATE EVENT
    ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'), -- MUSIC
    ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', 'd46e1f95-8a90-4745-8000-629f412bdbab'), -- CATERING
    ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', '6b351a75-3061-4d96-8856-d58f1576a568'), -- PHOTOGRAPHY
    ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', '3d5cb7b1-e512-4eae-bcd9-c2954b643b1b'), -- VIDEOGRAPHY
    ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', 'f9c3bc34-6316-47a1-b61a-85f842f8a76d'), -- GUEST TRANSPORTATION
    ('f726c1a3-13ea-4c5b-8dbf-30927310cb93', '3d0107f7-2cfa-4e95-b5b1-0136034602b6'), -- EVENT SECURITY

    -- BIRTHDAY PARTY
    ('2a3fbe6a-d495-4090-9e2e-09e2a4043460', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'), -- MUSIC
    ('2a3fbe6a-d495-4090-9e2e-09e2a4043460', 'd46e1f95-8a90-4745-8000-629f412bdbab'), -- CATERING
    ('2a3fbe6a-d495-4090-9e2e-09e2a4043460', '6b351a75-3061-4d96-8856-d58f1576a568'), -- PHOTOGRAPHY
    ('2a3fbe6a-d495-4090-9e2e-09e2a4043460', '3d0107f7-2cfa-4e95-b5b1-0136034602b6'), -- EVENT SECURITY

    -- OUTDOOR FESTIVAL
    ('15c1de85-50a4-4b60-a5c2-bb349d3173ab', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'), -- MUSIC

    -- GALA DINNER
    ('d1b237e6-d7a9-4797-b39f-9c2a1fcf93c0', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'), -- MUSIC
    ('d1b237e6-d7a9-4797-b39f-9c2a1fcf93c0', 'd46e1f95-8a90-4745-8000-629f412bdbab'), -- CATERING
    ('d1b237e6-d7a9-4797-b39f-9c2a1fcf93c0', '6b351a75-3061-4d96-8856-d58f1576a568'), -- PHOTOGRAPHY
    ('d1b237e6-d7a9-4797-b39f-9c2a1fcf93c0', '3d0107f7-2cfa-4e95-b5b1-0136034602b6'), -- EVENT SECURITY

    -- ENGAGEMENT PARTY
    ('17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'), -- MUSIC
    ('17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', 'd46e1f95-8a90-4745-8000-629f412bdbab'), -- CATERING
    ('17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', '6b351a75-3061-4d96-8856-d58f1576a568'), -- PHOTOGRAPHY
    ('17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', 'f9c3bc34-6316-47a1-b61a-85f842f8a76d'), -- GUEST TRANSPORTATION
    ('17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7', '3d0107f7-2cfa-4e95-b5b1-0136034602b6'), -- EVENT SECURITY

    -- COSTUME PARTY
    ('33a8ecb0-81a5-44a0-b07d-028b209ef4fd', 'a0c5c0b4-e85e-4655-8c62-5a5d9170b8b3'), -- MUSIC
    ('33a8ecb0-81a5-44a0-b07d-028b209ef4fd', '3d0107f7-2cfa-4e95-b5b1-0136034602b6'), -- EVENT SECURITY

    -- RELIGIOUS CEREMONY
    ('b740de8f-7a23-4fbb-a6ae-5b0e7777cd18', 'd46e1f95-8a90-4745-8000-629f412bdbab'), -- CATERING
    ('b740de8f-7a23-4fbb-a6ae-5b0e7777cd18', '6b351a75-3061-4d96-8856-d58f1576a568'), -- PHOTOGRAPHY
    ('b740de8f-7a23-4fbb-a6ae-5b0e7777cd18', '3d0107f7-2cfa-4e95-b5b1-0136034602b6'); -- EVENT SECURITY

-- ALLOWED CONCRETE PRODUCTS PER EVENT TYPE
-- IN EACH EVENT TYPE, ONE PRODUCT IS ADDED FOR EVERY RECOMMENDED CATEGORY (SPECIFYING EACH PRODUCT ACCORDING TO REAL LIFE NEEDS IS TOO MUCH WORK FOR LITTLE VALUE)
INSERT INTO versioned_product_eventtype (versioned_product_static_product_id, versioned_product_version, eventtype_id)
VALUES
    -- WEDDING
    ('5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832'), -- FIREWORK CATEGORY ('Fountain Fireworks')
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832'), -- DRINKS CATEGORY ('Champagne')
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832'), -- FOOD CATEGORY ('Finger Foods & Canapés')
    ('a3f5b299-1bc5-45ec-bb6e-b564e0d11c94', 1, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832'), -- DECORATION CATEGORY ('Flower Arrangements')

    -- TECHNO PARTY
    ('5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, 'cba94c6d-ef28-4de2-bbe7-0e1a7797d941'), -- FIREWORK CATEGORY ('Fountain Fireworks')
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, 'cba94c6d-ef28-4de2-bbe7-0e1a7797d941'), -- DRINKS CATEGORY ('Champagne')
    ('13b86db3-3e28-4f01-bb8b-97d9d0a7d5c1', 1, 'cba94c6d-ef28-4de2-bbe7-0e1a7797d941'), -- LIGHTNING CATEGORY ('Spotlight rental')
    ('7e9d4ad7-b82c-43b5-9a4c-9e343f038779', 1, 'cba94c6d-ef28-4de2-bbe7-0e1a7797d941'), -- STAGE & TENTS CATEGORY ('Stage Setup (Small)')

    -- CORPORATE EVENT
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- DRINKS CATEGORY ('Champagne')
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- FOOD CATEGORY ('Finger Foods & Canapés')
    ('13b86db3-3e28-4f01-bb8b-97d9d0a7d5c1', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- LIGHTNING CATEGORY ('Spotlight rental')
    ('7e9d4ad7-b82c-43b5-9a4c-9e343f038779', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- STAGE & TENTS CATEGORY ('Stage Setup (Small)')

    -- BIRTHDAY PARTY
    ('5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, '2a3fbe6a-d495-4090-9e2e-09e2a4043460'), -- FIREWORK CATEGORY ('Fountain Fireworks')
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, '2a3fbe6a-d495-4090-9e2e-09e2a4043460'), -- DRINKS CATEGORY ('Champagne')
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, '2a3fbe6a-d495-4090-9e2e-09e2a4043460'), -- FOOD CATEGORY ('Finger Foods & Canapés')
    ('a3f5b299-1bc5-45ec-bb6e-b564e0d11c94', 1, '2a3fbe6a-d495-4090-9e2e-09e2a4043460'), -- DECORATION CATEGORY ('Flower Arrangements')

    ('8e862226-257d-473c-94eb-aedff374dedf', 1, '2a3fbe6a-d495-4090-9e2e-09e2a4043460'),
    ('fdf4285e-1619-4f13-819a-0dc6843c4ce1', 1, '2a3fbe6a-d495-4090-9e2e-09e2a4043460'),
    ('11460238-d909-4b1e-ba1b-651904b36eb0', 1, '2a3fbe6a-d495-4090-9e2e-09e2a4043460'),

    -- OUTDOOR FESTIVAL
    ('5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, '15c1de85-50a4-4b60-a5c2-bb349d3173ab'), -- FIREWORK CATEGORY ('Fountain Fireworks')
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, '15c1de85-50a4-4b60-a5c2-bb349d3173ab'), -- DRINKS CATEGORY ('Champagne')
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, '15c1de85-50a4-4b60-a5c2-bb349d3173ab'), -- FOOD CATEGORY ('Finger Foods & Canapés')

    -- GALA DINNER
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, 'd1b237e6-d7a9-4797-b39f-9c2a1fcf93c0'), -- DRINKS CATEGORY ('Champagne')
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, 'd1b237e6-d7a9-4797-b39f-9c2a1fcf93c0'), -- FOOD CATEGORY ('Finger Foods & Canapés')
    ('a3f5b299-1bc5-45ec-bb6e-b564e0d11c94', 1, 'd1b237e6-d7a9-4797-b39f-9c2a1fcf93c0'), -- DECORATION CATEGORY ('Flower Arrangements')
    ('13b86db3-3e28-4f01-bb8b-97d9d0a7d5c1', 1, 'd1b237e6-d7a9-4797-b39f-9c2a1fcf93c0'), -- LIGHTNING CATEGORY ('Spotlight rental')

    -- ENGAGEMENT PARTY
    ('5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, '17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7'), -- FIREWORK CATEGORY ('Fountain Fireworks')
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, '17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7'), -- DRINKS CATEGORY ('Champagne')
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, '17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7'), -- FOOD CATEGORY ('Finger Foods & Canapés')
    ('a3f5b299-1bc5-45ec-bb6e-b564e0d11c94', 1, '17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7'), -- DECORATION CATEGORY ('Flower Arrangements')

    -- COSTUME PARTY
    ('5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, '33a8ecb0-81a5-44a0-b07d-028b209ef4fd'), -- FIREWORK CATEGORY ('Fountain Fireworks')
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, '33a8ecb0-81a5-44a0-b07d-028b209ef4fd'), -- DRINKS CATEGORY ('Champagne')
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, '33a8ecb0-81a5-44a0-b07d-028b209ef4fd'), -- FOOD CATEGORY ('Finger Foods & Canapés')
    ('a3f5b299-1bc5-45ec-bb6e-b564e0d11c94', 1, '33a8ecb0-81a5-44a0-b07d-028b209ef4fd'), -- DECORATION CATEGORY ('Flower Arrangements')
    ('13b86db3-3e28-4f01-bb8b-97d9d0a7d5c1', 1, '33a8ecb0-81a5-44a0-b07d-028b209ef4fd'), -- LIGHTNING CATEGORY ('Spotlight rental')

    -- RELIGIOUS CEREMONY
    ('763b0d82-81de-4a8c-8bba-45c19e688b31', 1, 'b740de8f-7a23-4fbb-a6ae-5b0e7777cd18'), -- DRINKS CATEGORY ('Champagne')
    ('e4042b8b-1a71-46fc-a4ca-289d39a9b575', 1, 'b740de8f-7a23-4fbb-a6ae-5b0e7777cd18'), -- FOOD CATEGORY ('Finger Foods & Canapés')
    ('a3f5b299-1bc5-45ec-bb6e-b564e0d11c94', 1, 'b740de8f-7a23-4fbb-a6ae-5b0e7777cd18'); -- DECORATION CATEGORY ('Flower Arrangements')

INSERT INTO versioned_service_eventtype (versioned_service_static_service_id, versioned_service_version, eventtype_id)
VALUES
    -- WEDDING
    ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832'), -- MUSIC CATEGORY ('Classic Jazz Band')
    ('deca359b-9bfb-4b6f-bc24-3e509f595da4', 1, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832'), -- CATERING CATEGORY ('Plated Dinner Service')
    ('9ee88634-aa10-48d1-b2c4-98556eac1684', 1, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832'), -- PHOTOGRAPHY CATEGORY ('Event Photography')
    ('8fb67698-2344-4b1d-950e-478c14f477cd', 1, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832'), -- VIDEOGRAPHY CATEGORY ('Live Streaming Videography')
    ('b008b01e-4f96-4233-873e-77617645c371', 1, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832'), -- GUEST TRANSPORTATION CATEGORY ('VIP Car Service')
    ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, 'a8b8d5b9-d1b2-47e1-b5a6-3efac3b6b832'), -- EVENT SECURITY CATEGORY ('Bouncer Security')

    -- TECHNO PARTY
    ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, 'cba94c6d-ef28-4de2-bbe7-0e1a7797d941'), -- MUSIC CATEGORY ('Classic Jazz Band')
    ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, 'cba94c6d-ef28-4de2-bbe7-0e1a7797d941'), -- EVENT SECURITY CATEGORY ('Bouncer Security')

    -- CORPORATE EVENT
    ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- MUSIC CATEGORY ('Classic Jazz Band')
    ('379624ba-652e-42e2-a7bb-d23a53ac2eed', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- MUSIC CATEGORY ('House DJ')
    ('c3210396-e7f0-445a-9c26-9b1aec7a3c4a', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- MUSIC CATEGORY ('Cover Band')
    ('deca359b-9bfb-4b6f-bc24-3e509f595da4', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- CATERING CATEGORY ('Plated Dinner Service')
    ('9ee88634-aa10-48d1-b2c4-98556eac1684', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- PHOTOGRAPHY CATEGORY ('Event Photography')
    ('8fb67698-2344-4b1d-950e-478c14f477cd', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- VIDEOGRAPHY CATEGORY ('Live Streaming Videography')
    ('b008b01e-4f96-4233-873e-77617645c371', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- GUEST TRANSPORTATION CATEGORY ('VIP Car Service')
    ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, 'f726c1a3-13ea-4c5b-8dbf-30927310cb93'), -- EVENT SECURITY CATEGORY ('Bouncer Security')

    -- BIRTHDAY PARTY
    ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, '2a3fbe6a-d495-4090-9e2e-09e2a4043460'), -- MUSIC CATEGORY ('Classic Jazz Band')
    ('deca359b-9bfb-4b6f-bc24-3e509f595da4', 1, '2a3fbe6a-d495-4090-9e2e-09e2a4043460'), -- CATERING CATEGORY ('Plated Dinner Service')
    ('9ee88634-aa10-48d1-b2c4-98556eac1684', 1, '2a3fbe6a-d495-4090-9e2e-09e2a4043460'), -- PHOTOGRAPHY CATEGORY ('Event Photography')
    ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, '2a3fbe6a-d495-4090-9e2e-09e2a4043460'), -- EVENT SECURITY CATEGORY ('Bouncer Security')

    -- OUTDOOR FESTIVAL
    ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, '15c1de85-50a4-4b60-a5c2-bb349d3173ab'), -- MUSIC CATEGORY ('Classic Jazz Band')

    -- GALA DINNER
    ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, 'd1b237e6-d7a9-4797-b39f-9c2a1fcf93c0'), -- MUSIC CATEGORY ('Classic Jazz Band')
    ('deca359b-9bfb-4b6f-bc24-3e509f595da4', 1, 'd1b237e6-d7a9-4797-b39f-9c2a1fcf93c0'), -- CATERING CATEGORY ('Plated Dinner Service')
    ('9ee88634-aa10-48d1-b2c4-98556eac1684', 1, 'd1b237e6-d7a9-4797-b39f-9c2a1fcf93c0'), -- PHOTOGRAPHY CATEGORY ('Event Photography')
    ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, 'd1b237e6-d7a9-4797-b39f-9c2a1fcf93c0'), -- EVENT SECURITY CATEGORY ('Bouncer Security')

    -- ENGAGEMENT PARTY
    ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, '17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7'), -- MUSIC CATEGORY ('Classic Jazz Band')
    ('deca359b-9bfb-4b6f-bc24-3e509f595da4', 1, '17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7'), -- CATERING CATEGORY ('Plated Dinner Service')
    ('9ee88634-aa10-48d1-b2c4-98556eac1684', 1, '17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7'), -- PHOTOGRAPHY CATEGORY ('Event Photography')
    ('b008b01e-4f96-4233-873e-77617645c371', 1, '17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7'), -- GUEST TRANSPORTATION CATEGORY ('VIP Car Service')
    ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, '17f2e64d-bbbe-4784-8cd9-0d98cbf95ad7'), -- EVENT SECURITY CATEGORY ('Bouncer Security')

    -- COSTUME PARTY
    ('daa22294-5377-487a-aa3f-7cd5a42cc568', 1, '33a8ecb0-81a5-44a0-b07d-028b209ef4fd'), -- MUSIC CATEGORY ('Classic Jazz Band')
    ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, '33a8ecb0-81a5-44a0-b07d-028b209ef4fd'), -- EVENT SECURITY CATEGORY ('Bouncer Security')

    -- RELIGIOUS CEREMONY
    ('deca359b-9bfb-4b6f-bc24-3e509f595da4', 1, 'b740de8f-7a23-4fbb-a6ae-5b0e7777cd18'), -- CATERING CATEGORY ('Plated Dinner Service')
    ('9ee88634-aa10-48d1-b2c4-98556eac1684', 1, 'b740de8f-7a23-4fbb-a6ae-5b0e7777cd18'), -- PHOTOGRAPHY CATEGORY ('Event Photography')
    ('9495a42f-fd67-44cf-8de8-1bc4b6df81c1', 1, 'b740de8f-7a23-4fbb-a6ae-5b0e7777cd18'); -- EVENT SECURITY CATEGORY ('Bouncer Security')

-- CHAT

INSERT INTO chat (id, chatter1_profile_id, chatter2_profile_id, product_static_product_id, product_version, service_static_service_id, service_version)
VALUES
    ('bfb226cb-6f35-43dc-a096-9fdee2c59f83', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456', '5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, null, null),
    ('780e7cde-7a99-4854-93b2-0ed2a7931c17', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91', '5a1b07b8-e918-4b0f-bcd2-7f1fd04dbb26', 1, null, null);

INSERT INTO chat_message (id, message, time, to_profile_id, chat)
VALUES
    -- BETWEEN GUEST AND EVENT ORGANIZER (GUEST, john.smith@example.com) (EVENT ORGANIZER, jane.smith@example.com)
    ('ee2ba33e-74c8-4b23-8016-55b1ecdef8f9', 'Hey! How have you been?', '2024-11-23T08:30:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('9797b593-84ef-421d-bde7-9fb15f1f534a', 'I’ve been good, just busy with work. What about you?', '2024-11-23T08:32:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('4c0f13e7-6bdf-49f5-b028-fea385256a6d', 'Same here. Been swamped with projects. Got any plans for the weekend?', '2024-11-23T08:34:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('7db54276-4767-4a05-bdf4-076536d6910b', 'Yeah, I’m going hiking on Saturday! Should be fun. What about you?', '2024-11-23T08:35:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('3ee1d615-7512-4b30-9e01-4d22db8cb7ab', 'That sounds great! I might just relax at home this time. Haven’t had a lazy weekend in a while.', '2024-11-23T08:37:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('321ae3c4-3f12-473e-9925-eca5dcf42deb', 'I get that! Everyone needs some downtime. Have you seen any good shows or movies recently?', '2024-11-23T08:40:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('7c794159-db52-4ce0-a754-eaa15e9bbe09', 'I started watching that new thriller series on Netflix. It’s pretty intense.', '2024-11-23T08:42:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('a2f5589c-f524-4d10-bfbf-570670dcb617', 'Ooh, sounds interesting! What’s it called?', '2024-11-23T08:44:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('f8c42e0f-3f90-4ae1-8658-4d3d648dd807', 'It’s called “The Silent Witness.” Really gripping! You should check it out.', '2024-11-23T08:46:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('42728107-bc12-4823-9195-df6979d8a5fa', 'I will! I’ve been looking for something new to watch. Thanks for the recommendation!', '2024-11-23T08:48:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('c98c3cc5-305c-4c24-aaef-e573d845f328', 'No problem! Let me know what you think once you start watching it.', '2024-11-23T08:50:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4', 'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('dc0f9747-2c8c-4924-aa87-fa1592ce6453', 'Will do! By the way, have you tried the new restaurant downtown?', '2024-11-23T08:52:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('469fc739-99b5-4672-8686-00e3d059be39', 'I haven’t yet. What kind of food do they serve?', '2024-11-23T08:55:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('8f8bbce6-61db-43c0-840f-a65d452147d1', 'It’s an Italian place. The pasta there is amazing!', '2024-11-23T08:57:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('ff3bf40c-6f5b-4230-8516-00e1a3250806', 'I love Italian food! I’ll have to check it out soon.', '2024-11-23T08:59:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('fb66f9ea-428c-41e9-9cfc-58060b6942dd', 'You won’t regret it. The tiramisu is out of this world!', '2024-11-23T09:01:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('81e04a04-8324-4b82-b4b8-f2bb6f8f739a', 'Tiramisu? Now I’m definitely sold. I’ll try it next time I’m there.', '2024-11-23T09:03:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('f10cf6a6-db5e-4138-95cd-14de410ec99e', 'You’ll love it! Anyway, do you have any exciting trips planned for the holidays?', '2024-11-23T09:05:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('df462078-39bf-49f2-8d66-7c967e8d5514', 'I’m thinking of going to the mountains for a few days to unwind. How about you?', '2024-11-23T09:07:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('1928bb0d-2fc4-443e-bf48-b798fab8b8ee', 'That sounds amazing. I’m heading to the beach for a week of relaxation.', '2024-11-23T09:09:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('d8b5e4a2-6d27-46fc-806e-6809fc74b6b9', 'The beach sounds perfect! Are you going somewhere tropical?', '2024-11-23T09:11:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4', 'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('3464c8bc-50a0-41d9-839e-9f0465674abd', 'Yes, to a small island in the Caribbean. I can’t wait!', '2024-11-23T09:13:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('d52dc504-424f-4738-8f60-4224208c4656', 'That sounds like a dream! Take lots of pictures!', '2024-11-23T09:15:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('f576c24a-907e-4ef7-8cd2-69a53a3a01e6', 'I definitely will! I’ll send you some when I’m there.', '2024-11-23T09:17:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('882bb87e-3c17-406c-962f-32366f5b44d6', 'I’ll look forward to it! Let me know if you need any recommendations for things to do.', '2024-11-23T09:19:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4', 'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('297759df-84ff-4b15-b8f6-c7f381645693', 'Thanks! I’m sure I’ll find plenty to do there. Maybe we can plan a trip together sometime.', '2024-11-23T09:21:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('8037830a-5280-40a9-8260-1847c251fc27', 'That would be awesome! We should definitely do that.', '2024-11-23T09:23:00', '9a4531e5-2fda-42bc-8355-d7991bfc8ff4',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),
    ('0a370703-8daf-40be-870c-1773f5de861b', 'I’m going to start researching some destinations. Let’s make it happen!', '2024-11-23T09:25:00', '3d82e9b8-3d9b-4c7d-b244-1e6725b78456',  'bfb226cb-6f35-43dc-a096-9fdee2c59f83'),

    -- BETWEEN EVENT ORGANIZER AND SELLER (EVENT ORGANIZER susan.brown@example.com) (SELLER, lily.martinez@example.com)
    ('51f8ca6c-1851-4e12-bf9a-e0994fa950fb', 'Hey! How are you doing?', '2024-11-23T10:00:00', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e', '780e7cde-7a99-4854-93b2-0ed2a7931c17'),
    ('35965c72-6835-4cd0-9a6d-cdcfa5f6890f', 'Hey! I’m doing well, thanks for asking. How about you?', '2024-11-23T10:02:00', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91', '780e7cde-7a99-4854-93b2-0ed2a7931c17'),
    ('8ca62c99-2556-482e-9e66-906fced369da', 'I’m good too, just a little tired. Busy week at work.', '2024-11-23T10:04:00', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e',  '780e7cde-7a99-4854-93b2-0ed2a7931c17'),
    ('e2319834-2771-4cbe-97b5-6a79892a55a8', 'I hear you. It’s been pretty hectic for me too. Got anything fun planned for the weekend?', '2024-11-23T10:06:00', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91',  '780e7cde-7a99-4854-93b2-0ed2a7931c17'),
    ('0646f9dc-4ada-4d51-b07c-b89e34f5d2b5', 'I’m actually going to a concert on Saturday night. Looking forward to it!', '2024-11-23T10:08:00', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e', '780e7cde-7a99-4854-93b2-0ed2a7931c17'),
    ('2a894bf0-70d1-47af-8fad-539944f2a7d0', 'That sounds awesome! Who’s playing?', '2024-11-23T10:10:00', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91',  '780e7cde-7a99-4854-93b2-0ed2a7931c17'),
    ('74171528-3ddd-40e0-8694-8f7673d6f11e', 'It’s a local indie band. They’re pretty good! I’ve been listening to them a lot lately.', '2024-11-23T10:12:00', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e',  '780e7cde-7a99-4854-93b2-0ed2a7931c17'),
    ('ac3e7bcd-389b-4bac-b316-4ef7343d5722', 'Nice, I bet it’s going to be a great show. I should check them out sometime.', '2024-11-23T10:14:00', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91',  '780e7cde-7a99-4854-93b2-0ed2a7931c17'),
    ('b45e3fe7-f786-4fea-bb53-be59e8eda997', 'You totally should! They’re really talented. What about you? Got any plans for the weekend?', '2024-11-23T10:16:00', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e', '780e7cde-7a99-4854-93b2-0ed2a7931c17'),
    ('eb1c0212-8fb1-4539-9846-ed18d875bad4', 'I’m going to a friend’s birthday party. Should be fun!', '2024-11-23T10:18:00', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91',  '780e7cde-7a99-4854-93b2-0ed2a7931c17'),
    ('58051851-5dfb-4f20-b1c8-381155b4deec', 'Nice! That sounds like a blast. What kind of party is it?', '2024-11-23T10:20:00', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e',  '780e7cde-7a99-4854-93b2-0ed2a7931c17'),
    ('6bb19501-52af-42db-abd4-eab4a626495f', 'It’s a casual get-together with close friends. We’ll probably just hang out, eat, and play some games.', '2024-11-23T10:22:00', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91',  '780e7cde-7a99-4854-93b2-0ed2a7931c17'),
    ('3e530c74-8f04-4574-8ad1-d932542c22ec', 'Sounds like a lot of fun! What kind of games do you guys play?', '2024-11-23T10:24:00', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e',  '780e7cde-7a99-4854-93b2-0ed2a7931c17'),
    ('3e81b26f-8a8c-4f2b-b741-91f957dbc07d', 'We usually play board games or card games, sometimes some video games. Everyone loves a good competition!', '2024-11-23T10:26:00', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91', '780e7cde-7a99-4854-93b2-0ed2a7931c17'),
    ('75224460-7445-4d88-85fd-541ed2268766', 'That’s awesome! I’ve been really into board games lately too. Got any recommendations?', '2024-11-23T10:28:00', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e', '780e7cde-7a99-4854-93b2-0ed2a7931c17'),
    ('cf555af8-df91-4bb0-b95f-76b19f727075', 'If you like strategy, “Catan” is a classic. Or “Ticket to Ride” for something a bit lighter.', '2024-11-23T10:30:00', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91',  '780e7cde-7a99-4854-93b2-0ed2a7931c17'),
    ('ded44c69-1baf-42d2-8d5c-00ecb0de2cd1', 'I love “Catan”! I’ll have to check out “Ticket to Ride.” Thanks for the tip!', '2024-11-23T10:32:00', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e', '780e7cde-7a99-4854-93b2-0ed2a7931c17'),
    ('e4cf9a34-a413-4ac8-adfd-9d14d342067c', 'No problem! I think you’ll really enjoy it. Let me know what you think after you try it out.', '2024-11-23T10:34:00', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91',  '780e7cde-7a99-4854-93b2-0ed2a7931c17'),
    ('a6825c50-22fc-4cce-b914-38cc03111a4a', 'Will do! I’m sure we’ll play it this weekend. Have a great time at the party!', '2024-11-23T10:36:00', 'a91f3db9-b5fe-4a7f-9d3f-299ab6164b2e',  '780e7cde-7a99-4854-93b2-0ed2a7931c17'),
    ('ae101a7e-8503-4f8f-ab1c-1736daf11688', 'Thanks, you too! Enjoy the concert!', '2024-11-23T10:38:00', 'db48e7ac-1d35-4d9d-8e09-bf2e86533b91', '780e7cde-7a99-4854-93b2-0ed2a7931c17');
