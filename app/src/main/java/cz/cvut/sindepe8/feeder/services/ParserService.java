package cz.cvut.sindepe8.feeder.services;

import java.util.List;

import cz.cvut.sindepe8.feeder.models.ArticleModel;

/**
 * Created by petrs on 13-Apr-18.
 */

// For parsing strings from https://www.mobilmania.cz/rss/sc-47/
public class ParserService {
    //public static List<ArticleModel> ParseArticles(String string){

    //}







    public static final String DEMO_FEED = "This XML file does not appear to have any style information associated with it. The document tree is shown below.\n" +
            "<rss xmlns:atom=\"http://www.w3.org/2005/Atom\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" version=\"2.0\">\n" +
            "<channel>\n" +
            "<atom:link href=\"https://www.mobilmania.cz/rss/sc-47/default.aspx?tm=989\" rel=\"self\" type=\"application/rss+xml\"/>\n" +
            "<title>MobilMania.cz</title>\n" +
            "<link>https://www.mobilmania.cz</link>\n" +
            "<description></description>\n" +
            "<language>cs</language>\n" +
            "<pubDate>Fri, 13 Apr 2018 17:00:00 GMT</pubDate>\n" +
            "<image>\n" +
            "<title>MobilMania.cz</title>\n" +
            "<url>\n" +
            "https://www.mobilmania.cz/Client.Images/Logos/logo-mobilmania-rss.gif\n" +
            "</url>\n" +
            "<link>https://www.mobilmania.cz</link>\n" +
            "</image>\n" +
            "<item>\n" +
            "<title>\n" +
            "Black Shark: Logo Xiaomi chybí a za herní ovladač se připlácí\n" +
            "</title>\n" +
            "<link>\n" +
            "https://www.mobilmania.cz/clanky/black-shark-logo-xiaomi-chybi-a-za-herni-ovladac-se-priplaci/sc-3-a-1341523/default.aspx\n" +
            "</link>\n" +
            "<guid isPermaLink=\"false\">1341523-mobilmania.cz</guid>\n" +
            "<description>\n" +
            "Nová čínská společnost Black Shark, v níž vlastní významný podíl společnost Xiaomi, představila očekávaný herní telefon Black Shark. Má nekompromisní výkon a snadno připojitelný gamepad. Herní ovladače však dostanete pouze na jednu stranu a navíc za ně budete muset připlatit. Hlavní konkurencí bude ...\n" +
            "</description>\n" +
            "<pubDate>Fri, 13 Apr 2018 14:34:00 GMT</pubDate>\n" +
            "<dc:creator>Filip Kůžel, Milan Měchura</dc:creator>\n" +
            "</item>\n" +
            "<item>\n" +
            "<title>\n" +
            "Vypálí Huawei všem rybník? V listopadu má představit skládací smartphone\n" +
            "</title>\n" +
            "<link>\n" +
            "https://www.mobilmania.cz/clanky/vypali-huawei-vsem-rybnik-v-listopadu-ma-predstavit-skladaci-smartphone/sc-3-a-1341516/default.aspx\n" +
            "</link>\n" +
            "<guid isPermaLink=\"false\">1341516-mobilmania.cz</guid>\n" +
            "<description>\n" +
            "Po týdnech odmlky se zase začíná hovořit o skládacích smartphonech. A pokud máme věřit jihokorejskému zdroji, vypadá to, že první skládací smartphone na světě nepředstaví Apple ani Samsung, nýbrž čínský Huawei. Ten již nyní odebírá ohebné OLED panely od LG, takže je jen otázkou času, než představí ...\n" +
            "</description>\n" +
            "<pubDate>Fri, 13 Apr 2018 11:00:00 GMT</pubDate>\n" +
            "<dc:creator>Martin Chroust</dc:creator>\n" +
            "</item>\n" +
            "<item>\n" +
            "<title>\n" +
            "Zadrhne se čínská kopírka? Zákon na ochranu duševního vlastnictví je hotový\n" +
            "</title>\n" +
            "<link>\n" +
            "https://www.mobilmania.cz/clanky/zadrhne-se-cinska-kopirka-zakon-na-ochranu-dusevniho-vlastnictvi-je-hotovy/sc-3-a-1341518/default.aspx\n" +
            "</link>\n" +
            "<guid isPermaLink=\"false\">1341518-mobilmania.cz</guid>\n" +
            "<description>\n" +
            "Čína bude trestat porušování práv duševního vlastnictví. Náhradu škody bude vyžadovat za nedodržení práv týkajících se obchodních značek, patentů a zeměpisných označení. Práci na represivním systému se úřadu podařilo urychlit ve spolupráci se zahraničními agenturami. Jedním dechem je však nutné ...\n" +
            "</description>\n" +
            "<pubDate>Fri, 13 Apr 2018 09:52:00 GMT</pubDate>\n" +
            "<dc:creator>E15.cz, Marek Schwarzmann</dc:creator>\n" +
            "</item>\n" +
            "<item>\n" +
            "<title>\n" +
            "3G je u nás mizerné, ale vypínat se zatím nebude. Na vině jsou zastaralé mobily\n" +
            "</title>\n" +
            "<link>\n" +
            "https://www.mobilmania.cz/clanky/3g-je-u-nas-mizerne-ale-vypinat-se-zatim-nebude-na-vine-jsou-zastarale-mobily/sc-3-a-1341517/default.aspx\n" +
            "</link>\n" +
            "<guid isPermaLink=\"false\">1341517-mobilmania.cz</guid>\n" +
            "<description>\n" +
            "Cca před pěti lety T-Mobile oznámil, že v roce 2018 začne vypínat 3G síť. V roce 2015 operátor upřesnil, že do pěti let bude 3G vypnuté úplně a vše v lednu 2016 potvrdil generální ředitel českého T-Mobilu Milan Vašina. Teď je rok 2018 a my jsme se zajímali, jak to s českým 3G vypadá nejen u ...\n" +
            "</description>\n" +
            "<pubDate>Fri, 13 Apr 2018 08:20:00 GMT</pubDate>\n" +
            "<dc:creator>Filip Kůžel</dc:creator>\n" +
            "</item>\n" +
            "<item>\n" +
            "<title>\n" +
            "Bezpečnostní aktualizace. Tvrdí, že se do telefonů dostaly, přesto je přeskakují\n" +
            "</title>\n" +
            "<link>\n" +
            "https://www.mobilmania.cz/clanky/bezpecnostni-aktualizace-tvrdi-ze-se-do-telefonu-dostaly-presto-je-preskakuji/sc-3-a-1341515/default.aspx\n" +
            "</link>\n" +
            "<guid isPermaLink=\"false\">1341515-mobilmania.cz</guid>\n" +
            "<description>\n" +
            "Německá firma SRL (Security Research Labs) zaměřená na bezpečnost zjistila, že mobilní výrobci často přeskakují aktualizace zabezpečení svých smartphonů, přestože tvrdí, že byly do telefonu již nainstalovány. Podle zjištění SRL se to týká Googlu, HTC, Samsungu, Sony, Motoroly, ZTE, TCL a dalších. ...\n" +
            "</description>\n" +
            "<pubDate>Fri, 13 Apr 2018 07:00:00 GMT</pubDate>\n" +
            "<dc:creator>Martin Chroust</dc:creator>\n" +
            "</item>\n" +
            "<item>\n" +
            "<title>\n" +
            "Vybrali jsme nejlepší telefony, které si v dubnu 2018 můžete koupit\n" +
            "</title>\n" +
            "<link>\n" +
            "https://www.mobilmania.cz/clanky/nejlepsi-telefony-ktere-si-muzete-koupit/sc-3-a-1337463/default.aspx\n" +
            "</link>\n" +
            "<guid isPermaLink=\"false\">1337463-mobilmania.cz</guid>\n" +
            "<description>\n" +
            "Vybíráme největší mobilní hity v několika kategoriích • Smartphony dělíme podle výbavy a ceny, aby si mohl vybrat každý • Své místo mají i tablety a tlačítkové telefony\n" +
            "</description>\n" +
            "<pubDate>Thu, 12 Apr 2018 19:23:00 GMT</pubDate>\n" +
            "<dc:creator>Jan Láska</dc:creator>\n" +
            "</item>\n" +
            "<item>\n" +
            "<title>\n" +
            "Chytré hodinky s nejdelším názvem vyrobí Hublot a budou ukrutně drahé\n" +
            "</title>\n" +
            "<link>\n" +
            "https://www.mobilmania.cz/clanky/chytre-hodinky-s-nejdelsim-nazvem-vyrobi-hublot-a-budou-ukrutne-drahe/sc-3-a-1341512/default.aspx\n" +
            "</link>\n" +
            "<guid isPermaLink=\"false\">1341512-mobilmania.cz</guid>\n" +
            "<description>\n" +
            "Trh s chytrými hodinkami je v útlumu, důkazem je i nedávno skončená hodinářská konference Baselworld, kde ze segmentu hodinek s operačním systémem byly představeny pouze dva modely, a to navíc oba v limitovaných edicích – obrněné Casio WSD-F20SC, kterého bude vyrobeno pouze 700 kusů, a potom ...\n" +
            "</description>\n" +
            "<pubDate>Thu, 12 Apr 2018 17:00:00 GMT</pubDate>\n" +
            "<dc:creator>Jan Láska</dc:creator>\n" +
            "</item>\n" +
            "<item>\n" +
            "<title>\n" +
            "Gameloft má novou mobilní pecku: akční MMORPG ze světa posedlých zombíků\n" +
            "</title>\n" +
            "<link>\n" +
            "https://www.mobilmania.cz/clanky/gameloft-ma-novou-mobilni-pecku-akcni-mmorpg-ze-sveta-posedlych-zombiku/sc-3-a-1341507/default.aspx\n" +
            "</link>\n" +
            "<guid isPermaLink=\"false\">1341507-mobilmania.cz</guid>\n" +
            "<description>\n" +
            "Herní studio Gameloft uvedlo na mobilní platformy iOS a Android novou herní pecku, kterou nazvalo Dead Rivals - Zombie MMO. Jedná se o postapokalyptické MMORPG s otevřeným světem, v němž se svým hrdinou likvidujete zombíky, příp. ostatní hráče v režimu PvP. Při startu hry si můžete vybrat jednu ze ...\n" +
            "</description>\n" +
            "<pubDate>Thu, 12 Apr 2018 14:00:00 GMT</pubDate>\n" +
            "<dc:creator>Martin Chroust</dc:creator>\n" +
            "</item>\n" +
            "<item>\n" +
            "<title>\n" +
            "Nový kryt udělá z Motorol pořádně ukřičený stereo reproduktor\n" +
            "</title>\n" +
            "<link>\n" +
            "https://www.mobilmania.cz/clanky/novy-kryt-udela-z-motorol-poradne-ukriceny-stereo-reproduktor/sc-3-a-1341511/default.aspx\n" +
            "</link>\n" +
            "<guid isPermaLink=\"false\">1341511-mobilmania.cz</guid>\n" +
            "<description>\n" +
            "Motorola představila nový rozšiřující kryt z řady Moto Mods, která je kompatibilní se smartphony Moto Z první, druhé a také nadcházející třetí generace. Tentokrát jde o další variaci na hlasitý reproduktor, jenž obohatí zvukové funkce smartphonu. Pod příslušenstvím tentokrát není podepsána značka ...\n" +
            "</description>\n" +
            "<pubDate>Thu, 12 Apr 2018 13:00:00 GMT</pubDate>\n" +
            "<dc:creator>Jan Láska</dc:creator>\n" +
            "</item>\n" +
            "<item>\n" +
            "<title>\n" +
            "LG chce ještě rychlejší aktualizace. Otevírá nové softwarové centrum pro smartphony\n" +
            "</title>\n" +
            "<link>\n" +
            "https://www.mobilmania.cz/clanky/lg-chce-jeste-rychlejsi-aktualizace-otevira-nove-softwarove-centrum-pro-smartphony/sc-3-a-1341509/default.aspx\n" +
            "</link>\n" +
            "<guid isPermaLink=\"false\">1341509-mobilmania.cz</guid>\n" +
            "<description>\n" +
            "LG se díky těsné spolupráci s Googlem vždy příkladně staralo o software svých smartphonů, vlajkové modely přicházejí s aktualizacemi obvykle mezi prvními. To ale Korejcům nestačí a chtějí ukázat, že umí být ještě flexibilnější. Společnost LG otevřela v Soulu nové pracoviště nazvané Software Upgrade ...\n" +
            "</description>\n" +
            "<pubDate>Thu, 12 Apr 2018 12:15:00 GMT</pubDate>\n" +
            "<dc:creator>Jan Láska</dc:creator>\n" +
            "</item>\n" +
            "<item>\n" +
            "<title>\n" +
            "Samsung vysvětluje, jak dosáhl kvalitního sterea, i když každý reproduktor míří jinam\n" +
            "</title>\n" +
            "<link>\n" +
            "https://samsungmania.mobilmania.cz/clanky/samsung-vysvetluje-jak-dosahl-kvalitniho-sterea-i-kdyz-kazdy-reproduktor-miri-jinam/sc-309-a-1341500/default.aspx\n" +
            "</link>\n" +
            "<guid isPermaLink=\"false\">1341500-mobilmania.cz</guid>\n" +
            "<description>\n" +
            "Galaxy S9 má stereoreproduktory. Nedočkali jsme se ale klasického uspořádání dvou reproduktorů na čelní straně telefonu. Hlavní je na spodní hraně, jako druhý se počítá sluchátko nad displejem. O jejich výrobu se postarala značka AKG/Harman Kardon, kterou Samsung vlastní. Reproduktory prý ladil ...\n" +
            "</description>\n" +
            "<pubDate>Thu, 12 Apr 2018 12:00:00 GMT</pubDate>\n" +
            "<dc:creator>Martin Chroust</dc:creator>\n" +
            "</item>\n" +
            "<item>\n" +
            "<title>\n" +
            "V Antutu se objevilo monstrum, které deklasuje současnou špičku. Ponese značku Huawei\n" +
            "</title>\n" +
            "<link>\n" +
            "https://www.mobilmania.cz/clanky/v-antutu-se-objevilo-monstrum-ktere-deklasuje-soucasnou-spicku-ponese-znacku-huawei/sc-3-a-1341508/default.aspx\n" +
            "</link>\n" +
            "<guid isPermaLink=\"false\">1341508-mobilmania.cz</guid>\n" +
            "<description>\n" +
            "Nejvýkonnější mobil v (dnes už lehce neaktuálním) žebříčku Antutu má přes 213 tisíc bodů. Jakmile se přidají čerstvě prodávané modely jako Galaxy S9, nejvyšší hodnoty poskočí někam k 250 tisícům. To ale stále není nic proti tomu, co chystá Huawei. V Antutu se objevilo monstrum, které překonalo ...\n" +
            "</description>\n" +
            "<pubDate>Thu, 12 Apr 2018 11:44:00 GMT</pubDate>\n" +
            "<dc:creator>Filip Kůžel</dc:creator>\n" +
            "</item>\n" +
            "<item>\n" +
            "<title>\n" +
            "Dvě Nokie s čistým Androidem jdou do prodeje. S cenou to nepřehání, ale ani se nepodbízí\n" +
            "</title>\n" +
            "<link>\n" +
            "https://www.mobilmania.cz/clanky/dve-nokie-s-cistym-androidem-jdou-do-prodeje-s-cenou-to-neprehani-ale-ani-se-nepodbizi/sc-3-a-1341491/default.aspx\n" +
            "</link>\n" +
            "<guid isPermaLink=\"false\">1341491-mobilmania.cz</guid>\n" +
            "<description>\n" +
            "Během nejbližších týdnů se do českých obchodů dostane dvojice smartphonů Nokia představených na únorovém veletrhu v Barceloně. Nokia 7 Plus bude stát 9 999 Kč, Nokia 6.1 přijde na 6 999 Kč. Novinky přijdou do prodeje na konci dubna. Obě zařízení míří do střední nebo vyšší střední třídy a jsou ...\n" +
            "</description>\n" +
            "<pubDate>Thu, 12 Apr 2018 10:00:00 GMT</pubDate>\n" +
            "<dc:creator>Jan Láska</dc:creator>\n" +
            "</item>\n" +
            "<item>\n" +
            "<title>\n" +
            "Samsung začal prodávat dokovací stanici DeX. Z mobilu udělá počítač\n" +
            "</title>\n" +
            "<link>\n" +
            "https://samsungmania.mobilmania.cz/clanky/samsung-zacal-prodavat-dokovaci-stanici-dex-z-mobilu-udela-pocitac/sc-309-a-1341506/default.aspx\n" +
            "</link>\n" +
            "<guid isPermaLink=\"false\">1341506-mobilmania.cz</guid>\n" +
            "<description>\n" +
            "Samsung dnes začal prodávat dokovací stanici DeX Pad, která je určena pro aktuální vlajkové modely Galaxy S9 a Galaxy S9+. Podložka je určena jako nutný doplněk pro desktopový režim DeX, který debutoval již u loňského Galaxy S8. Recenzi „počítačového“ prostředí jste si mohli přečíst zde. A v čem ...\n" +
            "</description>\n" +
            "<pubDate>Thu, 12 Apr 2018 08:30:00 GMT</pubDate>\n" +
            "<dc:creator>Martin Chroust</dc:creator>\n" +
            "</item>\n" +
            "<item>\n" +
            "<title>\n" +
            "Tak vypadá operátorská noční můra. UPC Horizon Go je televize, kterou můžete vozit po celé EU\n" +
            "</title>\n" +
            "<link>\n" +
            "https://www.mobilmania.cz/clanky/tak-vypada-operatorska-nocni-mura-upc-horizon-go-je-televize-kterou-muzete-vozit-po-cele-eu/sc-3-a-1341501/default.aspx\n" +
            "</link>\n" +
            "<guid isPermaLink=\"false\">1341501-mobilmania.cz</guid>\n" +
            "<description>\n" +
            "Společnost UPC oznámila, že její mobilní televize Horizon Go (Android / Apple iOS) je nově dostupná po celé EU. Uživatelé tak mohou na služební cestě nebo dovolené sledovat více než 100 televizních stanic včetně sedmidenního zpětného zhlédnutí a online videotéky MyPrime (více než 2 000 filmových a ...\n" +
            "</description>\n" +
            "<pubDate>Thu, 12 Apr 2018 05:00:00 GMT</pubDate>\n" +
            "<dc:creator>Filip Kůžel</dc:creator>\n" +
            "</item>\n" +
            "<item>\n" +
            "<title>\n" +
            "RECENZE: Nokia 2 je hezká na povrchu, ale dutá uvnitř\n" +
            "</title>\n" +
            "<link>\n" +
            "https://www.mobilmania.cz/clanky/recenze-nokia-2-je-hezka-na-povrchu-ale-duta-uvnitr/sc-3-a-1341477/default.aspx\n" +
            "</link>\n" +
            "<guid isPermaLink=\"false\">1341477-mobilmania.cz</guid>\n" +
            "<description>\n" +
            "Nokia 2 je vstupenkou do světa chytrých telefonů • Největším tahákem je obrovská 4 100mAh baterie • Mnohé však může odradit více než 2 roky starý čipset\n" +
            "</description>\n" +
            "<pubDate>Wed, 11 Apr 2018 22:00:00 GMT</pubDate>\n" +
            "<dc:creator>Milan Měchura</dc:creator>\n" +
            "</item>\n" +
            "</channel>\n" +
            "</rss>";
}
