package com.tec.ine.models;

import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Town {
    private String id;
    private String name;

    private Map<String, String> localities;
    private Map<String, String> sections;

    private static ArrayList<Town> towns;

    private static Random randomGenerator = new Random();

    public static ArrayList<Town> getTowns() {
        if (towns == null) {
            towns = new ArrayList<>();
            fetchStates();
        }
        return towns;
    }

    private static void fetchStates() {

        Town town;
        Map<String, String> localities;
        Map<String, String> sections;
        town = new Town("001", "ACAMBAY");
        localities = new ArrayMap<>();
        localities.put("0007", "BOSHI GRANDE");
        localities.put("0017", "DONGU");
        localities.put("0037", "PUEBLO NUEVO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("002", "ACOLMAN");
        localities = new ArrayMap<>();
        localities.put("0015", "TEPEXPAN");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("004", "ALMOLOYA DE ALQUISIRAS");
        localities = new ArrayMap<>();
        localities.put("0003", "AGUA FRIA");
        localities.put("0015", "SAN ANDRES TEPETITLAN");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("005", "ALMOLOYA DE JUAREZ");
        localities = new ArrayMap<>();
        localities.put("0029", "SAN ANTONIO BUENAVISTA");
        localities.put("0032", "SAN FRANCISCO TLALCILALCALPAN");
        localities.put("0036", "SAN NICOLAS AMEALCO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("006", "ALMOLOYA DEL RIO");
        localities = new ArrayMap<>();
        localities.put("0001", "ALMOLOYA DEL RIO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("007", "AMANALCO");
        localities = new ArrayMap<>();
        localities.put("0009", "POTRERO EL");
        localities.put("0021", "POLVILLOS");
        localities.put("0030", "PROVIDENCIA LA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("008", "AMATEPEC");
        localities = new ArrayMap<>();
        localities.put("0001", "AMATEPEC");
        localities.put("0042", "SAN SIMON");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("009", "AMECAMECA");
        localities = new ArrayMap<>();
        localities.put("0006", "SAN PEDRO NEXAPA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("010", "APAXCO");
        localities = new ArrayMap<>();
        localities.put("0004", "PEREZ DE GALEANA");
        localities.put("0005", "LOMA BONITA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("011", "ATENCO");
        localities = new ArrayMap<>();
        localities.put("0006", "FRANCISCO I. MADERO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("012", "ATIZAPAN");
        localities = new ArrayMap<>();
        localities.put("0004", "LIBERTAD LA");
        localities.put("013 ", "ATIZAPAN DE ZARAGOZA");
        localities.put("0001", "ATIZAPAN DE ZARAGOZA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("014", "ATLACOMULCO");
        localities = new ArrayMap<>();
        localities.put("0001", "ATLACOMULCO");
        localities.put("0008", "BOMBATEVI EJIDO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("0015", "LAS FUENTES");
        localities = new ArrayMap<>();
        localities.put("0035", "SAN ANTONIO ENCHISI");
        localities.put("0045", "SAN JOSE DEL TUNAL");
        localities.put("015 ", "ATLAUTLA");
        localities.put("0005", "SAN ANDRES TLALAMAC (TLALAMAC)");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("018", "CALIMAYA");
        localities = new ArrayMap<>();
        localities.put("0004", "SAN BARTOLITO TLATELOLCO");
        localities.put("0010", "ZARAGOZA DE GUADALUPE (ZARAGOZA)");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("019", "CAPULHUAC");
        localities = new ArrayMap<>();
        localities.put("0001", "CAPULHUAC");
        localities.put("0004", "SAN MIGUEL ALMAYA");
        localities.put("0005", "SAN NICOLAS TLAZALA");
        localities.put("0022", "SANTA MARIA COAXUSCO (SANTA MARIA)");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("020", "COACALCO DE BERRIOZABAL");
        localities = new ArrayMap<>();
        localities.put("0001", "COACALCO DE BERRIOZABAL");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("021", "COATEPEC HARINAS");
        localities = new ArrayMap<>();
        localities.put("0002", "ACUITLAPILCO");
        localities.put("0004", "AGUA BENDITA");
        localities.put("0017", "LOMA DE ACUITLAPILCO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("022", "COCOTITLAN");
        localities = new ArrayMap<>();
        localities.put("0001", "COCOTITLAN");
        localities.put("0002", "SAN ANDRES METLA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("023", "COYOTEPEC");
        localities = new ArrayMap<>();
        localities.put("0015", "SAN FRANCISCO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("024", "CUAUTITLAN");
        localities = new ArrayMap<>();
        localities.put("0001", "CUAUTITLAN");
        localities.put("0030", "RANCHO SAN ROQUE O RCHO.PRIETO (RANCHO SAN RO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("025", "CHALCO");
        localities = new ArrayMap<>();
        localities.put("0001", "CHALCO");
        localities.put("0014", "SAN MARTIN CUAUTLALPAN");
        localities.put("0017", "SAN MATEO TEZOQUIAPAN (MIRAFLORES)");
        localities.put("0019", "SAN PABLO ATLAZALPAN");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("026", "CHAPA DE MOTA");
        localities = new ArrayMap<>();
        localities.put("0001", "CHAPA DE MOTA");
        localities.put("0006", "CADENQUI");
        localities.put("0019", "QUINTE EL");
        localities.put("0021", "SAN FRANCISCO DE LAS TLABLAS");
        localities.put("0023", "SAN JUAN TUXTEPEC");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("027", "CHAPULTEPEC");
        localities = new ArrayMap<>();
        localities.put("0001", "CHAPULTEPEC");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("028", "CHIAUTLA");
        localities = new ArrayMap<>();
        localities.put("0001", "CHIAUTLA");
        localities.put("0007", "TEPETITLAN (SAN ANTONIO TEPETITLAN)");
        localities.put("0008", "TLATECAHUACAN");
        localities.put("0014", "CONCEPCION");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("029", "CHICOLOAPAN");
        localities = new ArrayMap<>();
        localities.put("0001", "CHICOLOAPAN");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("031", "CHIMALHUACAN");
        localities = new ArrayMap<>();
        localities.put("0001", "CHIMALHUACAN");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("032", "DONATO GUERRA");
        localities = new ArrayMap<>();
        localities.put("0002", "BATAN CHICO");
        localities.put("0011", "SAN FRANCISCO MIHUALTEPEC");
        localities.put("0012", "SAN JUAN XOCONUSCO");
        localities.put("0017", "SAN SIMON DE LA LAGUNA");
        localities.put("0018", "SANTIAGO HUITLAPALTEPEC");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("033", "ECATEPEC");
        localities = new ArrayMap<>();
        localities.put("0001", "ECATEPEC DE MORELOS");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("034", "ECATZINGO");
        localities = new ArrayMap<>();
        localities.put("0001", "ECATZINGO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("035", "HUEHUETOCA");
        localities = new ArrayMap<>();
        localities.put("0001", "HUEHUETOCA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("037", "HUIXQUILUCAN");
        localities = new ArrayMap<>();
        localities.put("0001", "HUIXQUILUCAN");
        localities.put("0007", "GENERAL IGNACIO ALLENDE");
        localities.put("0018", "SAN BARTOLOME COATEPEC");
        localities.put("0020", "SAN FERNANDO");
        localities.put("0026", "ZACAMULPA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("038", "ISIDRO FABELA");
        localities = new ArrayMap<>();
        localities.put("0003", "CAÑADA DE ONOFRES");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("039", "IXTAPALUCA");
        localities = new ArrayMap<>();
        localities.put("0001", "IXTAPALUCA");
        localities.put("0011", "RIO FRIO DE JUAREZ");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("040", "IXTAPAN DE LA SAL");
        localities = new ArrayMap<>();
        localities.put("0008", "MESON NUEVO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("041", "IXTAPAN DEL ORO");
        localities = new ArrayMap<>();
        localities.put("0002", "CALERA LA (LOS GALLOS)");
        localities.put("0004", "MIAHUATLAN DE HIDALGO");
        localities.put("0009", "TUTUAPAN");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("042", "IXTLAHUACA");
        localities = new ArrayMap<>();
        localities.put("0001", "IXTLAHUACA");
        localities.put("0006", "GUADALUPANA LA");
        localities.put("0017", "SAN CRISTOBAL LOS BAÑOS");
        localities.put("0018", "SAN FRANCISCO DE GUZMAN");
        localities.put("0038", "SAN MIGUEL EL ALTO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("0041", "SAN FRANCISCO DEL RIO");
        localities = new ArrayMap<>();
        localities.put("0072", "JALPA DE LOS BAÑOS");
        localities.put("0075", "SAN CRISTOBAL DE LOS BAÑOS");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("043", "XALATLACO");
        localities = new ArrayMap<>();
        localities.put("0001", "XALATLACO");
        localities.put("0004", "MESA LA (EL AGUILA)");
        localities.put("0008", "SAN JUAN TOMASQUILLO HERRADURA (SAN JUAN TOMA");
        localities.put("0010", "TEJOCOTES LOS");
        localities.put("0018", "AGUILA, EL");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("045", "JILOTEPEC");
        localities = new ArrayMap<>();
        localities.put("0001", "JILOTEPEC");
        localities.put("0005", "BUENAVISTA");
        localities.put("0007", "CANALEJAS");
        localities.put("0009", "COSCOMATE DEL PROGRESO (COSCOMATE)");
        localities.put("0021", "RINCON EL");
        localities.put("0032", "XHIXHATA");
        localities.put("0094", "SAN VICENTE");
        localities.put("0110", "EJIDO DE JILOTEPEC");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("047", "JIQUIPILCO");
        localities = new ArrayMap<>();
        localities.put("0007", "LOMA DE ENDOTZI");
        localities.put("0009", "LOMA DEL ASTILLERO (LOMA DEL SITIO)");
        localities.put("0018", "SAN ANTONIO NIGINI (SAN ANTONIO NIXINI)");
        localities.put("0019", "SAN BARTOLO OXTOTITLAN");
        localities.put("0023", "SAN MARTIN MORELOS");
        localities.put("0025", "SANTA CRUZ TEPEXPAN");
        localities.put("0058", "TIERRA BLANCA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("048", "JOCOTITLAN");
        localities = new ArrayMap<>();
        localities.put("0012", "SAN JUAN COAJOMULCO");
        localities.put("0018", "SANTIAGO YECHE");
        localities.put("0020", "RUSO, EL");
        localities.put("0034", "SAN MARCOS COAJOMULCO (SAN MARCOS)");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("051", "LERMA");
        localities = new ArrayMap<>();
        localities.put("0003", "ANALCO (COLONIA AGRICOLA)");
        localities.put("0007", "COLONIA ALVARO OBREGON");
        localities.put("0016", "SAN AGUSTIN HUITZIZILAPAN");
        localities.put("0020", "SAN MATEO ATARASQUILLO");
        localities.put("0027", "SANTA MARIA TLALMIMILOLPAN");
        localities.put("0028", "SANTIAGO ANALCO");
        localities.put("0031", "ADOLFO LOPEZ MATEOS HUITZIZILAPAN COLONIA");
        localities.put("0050", "REFORMA TLALMIMILOLPAN COLONIA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("052", "MALINALCO");
        localities = new ArrayMap<>();
        localities.put("0017", "SAN SEBASTIAN");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("054", "METEPEC");
        localities = new ArrayMap<>();
        localities.put("0001", "METEPEC");
        localities.put("0032", "SAN BARTOLOME TLALTELULCO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("056", "MORELOS");
        localities = new ArrayMap<>();
        localities.put("0001", "MORELOS");
        localities.put("0008", "SAN SEBASTIAN (SAN SEBASTIAN BUENOS AIRES)");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("057", "NAUCALPAN DE JUAREZ");
        localities = new ArrayMap<>();
        localities.put("0001", "NAUCALPAN DE JUAREZ");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("058", "NEZAHUALCOYOTL");
        localities = new ArrayMap<>();
        localities.put("0001", "NEZAHUALCOYOTL");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("059", "NEXTLALPAN");
        localities = new ArrayMap<>();
        localities.put("0001", "NEXTLALPAN");
        localities.put("0019", "SANTIAGO ATOCAN");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("060", "NICOLAS ROMERO");
        localities = new ArrayMap<>();
        localities.put("0001", "NICOLAS ROMERO");
        localities.put("0071", "COLONIA EL MIRADOR");
        localities.put("0081", "NUEVO EJIDO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("061", "NOPALTEPEC");
        localities = new ArrayMap<>();
        localities.put("0001", "NOPALTEPEC");
        localities.put("0005", "SAN MIGUEL ATEPOXCO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("062", "OCOYOACAC");
        localities = new ArrayMap<>();
        localities.put("0004", "COLONIA JUAREZ (LOS CHIRINOS)");
        localities.put("0011", "LLANITO DE SAN ANTONIO EL");
        localities.put("0018", "MARQUEZA LA");
        localities.put("0022", "SAN PEDRO ATLAPULCO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("063", "OCUILAN");
        localities = new ArrayMap<>();
        localities.put("0009", "COYOLTEPEC");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("064", "EL ORO");
        localities = new ArrayMap<>();
        localities.put("0001", "ORO, EL");
        localities.put("0012", "ADOLFO LOPEZ MATEOS");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("065", "OTUMBA");
        localities = new ArrayMap<>();
        localities.put("0014", "SAN FRANCISCO TLALTICA");
        localities.put("0019", "SANTIAGO TOLMAN");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("066", "OTZOLOAPAN");
        localities = new ArrayMap<>();
        localities.put("0019", "SAN MIGUEL PIRU (EL PIRU)");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("067", "OTZOLOTEPEC");
        localities = new ArrayMap<>();
        localities.put("0009", "SAN AGUSTIN MIMBRES");
        localities.put("0012", "SAN MATEO CAPULHUAC");
        localities.put("0018", "Y LA");
        localities.put("0019", "CAPULIN EL");
        localities.put("0034", "PURISIMA CONCEPCION LA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("068", "OZUMBA");
        localities = new ArrayMap<>();
        localities.put("0001", "OZUMBA");
        localities.put("0008", "SANTIAGO MAMALHUAZUCA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("070", "PAZ,LA");
        localities = new ArrayMap<>();
        localities.put("0001", "PAZ, LA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("072", "RAYON");
        localities = new ArrayMap<>();
        localities.put("0001", "RAYON");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("074", "SAN FELIPEDEL, PROGRESO");
        localities = new ArrayMap<>();
        localities.put("0001", "SAN FELIPE DEL PROGRESO");
        localities.put("0010", "CARMEN OCOTEPEC");
        localities.put("0031", "ESTUTEMPAN");
        localities.put("0068", "ROSA DEL CALVARIO (RANCHERIA LA ROSA)");
        localities.put("0093", "SAN LUCAS OCOTEPEC");
        localities.put("0098", "SAN NICOLAS GUADALUPE");
        localities.put("0099", "SAN NICOLAS MAVATI");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("075", "SAN MARTIN DE LAS, PIRAMIDES");
        localities = new ArrayMap<>();
        localities.put("0008", "SAN PABLO IXQUITLAN");
        localities.put("0009", "SANTA MARIA PALAPA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("076", "SAN MATEO ATENCO");
        localities = new ArrayMap<>();
        localities.put("0001", "SAN MATEO ATENCO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("078", "SANTO TOMAS");
        localities = new ArrayMap<>();
        localities.put("0026", "LAS FINCAS");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("079", "SOYANIQUILPAN DE , JUAREZ");
        localities = new ArrayMap<>();
        localities.put("0007", "SAN AGUSTIN BUENAVISTA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("080", "SULTEPEC");
        localities = new ArrayMap<>();
        localities.put("0032", "SAN PEDRO HUEYAHUALCO");
        localities.put("0034", "SANTO TOMAS DE LAS FLORES SANTO TOMAS");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("081", "TECAMAC");
        localities = new ArrayMap<>();
        localities.put("0001", "TECAMAC");
        localities.put("0019", "OJO DE AGUA");
        localities.put("0025", "SAN MARTIN AZCATEPEC");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("082", "TEJUPILCO");
        localities = new ArrayMap<>();
        localities.put("0001", "TEJUPILCO");
        localities.put("0022", "BEJUCOS");
        localities.put("0057", "CUADRILLA DE MARTINEZ (LOS MARTINEZ)");
        localities.put("0076", "SAN MIGUEL IXTAPAN");
        localities.put("0097", "MONTE DE DIOS");
        localities.put("0138", "RINCON DE AGUIRRE");
        localities.put("0171", "TENERIA (PUEBLO NUEVO)");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("083", "TEMAMATLA");
        localities = new ArrayMap<>();
        localities.put("0002", "REYES ACATLIZHUAYAN LOS (ACATLIHUAYAN)");
        localities.put("0003", "SANTIAGO ZULA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("084", "TEMASCALAPA");
        localities = new ArrayMap<>();
        localities.put("0018", "SANTA MARIA MAQUIXCO (EL ALTO)");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("085", "TEMASCALCINGO");
        localities = new ArrayMap<>();
        localities.put("0014", "MAGDALENA LA");
        localities.put("0017", "MESA DE SANTIAGO (LA MESA)");
        localities.put("0035", "SANTA MARIA CANCHESDA");
        localities.put("0040", "GARAY, EL");
        localities.put("0066", "MAGDALENA CRUZ BLANCA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("086", "TEMASCALTEPEC");
        localities = new ArrayMap<>();
        localities.put("0003", "CARBONERAS");
        localities.put("0010", "FINCA LA");
        localities.put("0023", "RINCON DE ATARASQUILLO");
        localities.put("0028", "SAN ANTONIO ALBARRANES");
        localities.put("0032", "SAN MIGUEL OXTOTILPAN");
        localities.put("0033", "SAN PEDRO TENAYAC");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("087", "TEMOAYA");
        localities = new ArrayMap<>();
        localities.put("0030", "SAN PEDRO ARRIBA");
        localities.put("0035", "TLALTENANGUITO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("088", "TENANCINGO");
        localities.put("0023", "SAN SIMONITO");
        localities.put("089", "TENANGO DEL AIRE");
        localities.put("0004", "SANTIAGO TEPOPULA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("090", "TENANGO DEL VALLE");
        localities.put("0038", "SANTA MARIA JAJALPA");
        localities.put("0039", "SANTIAGUITO CUAXUXTENCO (SAN JUAN LA ISLA)");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("091", "TEOLOYUCAN");
        localities.put("0012", "SAN SEBASTIAN");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("092", "TEOTIHUACAN");
        localities.put("0001", "TEOTIHUACAN");
        localities.put("0020", "SAN SEBASTIAN XOLAPA");
        localities.put("0022", "SANTIAGO ZACUALUCA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("093", "TEPETLAOXTOC");
        localities.put("0018", "LOMA LA (COLONIA LA LOMA)");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("094", "TEPETLIXPA");
        localities.put("0001", "TEPETLIXPA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("095", "TEPOTZOTLAN");
        localities.put("0010", "DOLORES LOS");
        localities.put("0023", "SAN MIGUEL CAÑADAS");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("096", "TEQUIXQUIAC");
        localities.put("0001", "TEQUIXQUIAC");
        localities.put("0012", "COLONIA WENCESLAO LABRA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("098", "TEXCALYACAC");
        localities.put("0001", "TEXCALYACAC");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("099", "TEXCOCO");
        localities.put("0001", "TEXCOCO");
        localities.put("0024", "SAN JERONIMO AMANALCO");
        localities.put("0030", "SAN MIGUEL TLAIXPAN");
        localities.put("0035", "SANTA CATARINA DEL MONTE");
        localities.put("0041", "SANTA MARIA TECUANULCO");
        localities.put("0045", "TULANTONGO");
        localities.put("0096", "PALO GACHO");
        localities.put("0130", "SAN FELIPE DE JESUS");
        localities.put("0140", "COLONIA SAN JOS•AMANALCO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("100", "TEZOYUCA");
        localities.put("0002", "TEQUISISTLAN");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("101", "TIANGUISTENCO");
        localities.put("0001", "TIANGUISTENCO");
        localities.put("0003", "SAN NICOLAS COATEPEC");
        localities.put("0006", "GUADALUPE YANCUITLALPAN");
        localities.put("0014", "SAN PEDRO TLALTIZAPAN");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("102", "TIMILPAN");
        localities.put("0010", "RINCON DE BUCIO MANZANA 1RA Y 2DA");
        localities.put("0011", "YONDEJE (SAN ANTONIO YONDEJE)");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("103", "TLALMANALCO");
        localities.put("0001", "TLALMANALCO");
        localities.put("0046", "SAN CRISTOBAL TEZOPILO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("104", "TLALNEPANTLA DE BAZ");
        localities.put("0001", "TLALNEPANTLA DE BAZ");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("106", "TOLUCA");
        localities.put("0001", "TOLUCA");
        localities.put("0043", "CACALOMACAN");
        localities.put("0050", "CERRILLO VISTA HERMOSA (EL CERRILLO)");
        localities.put("0062", "SAN ANDRES CUEXCONTITLAN");
        localities.put("0071", "SAN FRANCISCO TOTOLTEPEC");
        localities.put("0072", "SAN JOSE GUADALUPE (SAN JOSE GUADALUPE OTZACA");
        localities.put("0078", "SAN MARTIN TOLTEPEC");
        localities.put("0079", "SAN MATEO OTZACATIPAN");
        localities.put("0137", "EJIDO STA CRUZ ATZCAPOZALTONGO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("107", "TONATICO");
        localities.put("0004", "PUERTA DE SANTIAGO LA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("108", "TULTEPEC");
        localities.put("0001", "TULTEPEC");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("109", "TULTITLAN");
        localities.put("0001", "TULTITLAN");
        localities.put("0003", "BUENAVISTA");
        localities.put("0068", "FUENTES DEL VALLE");
        localities.put("0069", "AMPLIACION SAN MATEO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("110", "VALLE DE BRAVO");
        localities.put("0012", "COLORINES");
        localities.put("0027", "RINCON VILLA DEL VALLE");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("111", "VILLA DE ALLENDE");
        localities.put("0001", "VILLA DE ALLENDE");
        localities.put("0013", "JACAL EL");
        localities.put("0028", "SAN ILDEFONSO");
        localities.put("0030", "SAN JERONIMO TOTOLTEPEC");
        localities.put("0034", "SAN PABLO MALACATEPEC");
        localities.put("0041", "MAQUINA, LA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("112", "VILLA DEL CARBON");
        localities.put("0012", "SAN LUIS LOMA ALTA TASHIMAY");
        localities.put("0013", "LLANO DE ZACAPEXCO");
        localities.put("0022", "SAN JERONIMO ZACAPEXCO");
        localities.put("0023", "SAN LUIS TAXHIMAY");
        localities.put("0037", "ARENAL EL");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("113", "VILLA GUERRERO");
        localities.put("0007", "ISLOTE EL");
        localities.put("0033", "EJIDO DE LA FINCA");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("114", "VILLA VICTORIA");
        localities.put("0001", "VILLA VICTORIA");
        localities.put("0004", "CEDROS LOS");
        localities.put("0008", "ESPINAL EL");
        localities.put("0014", "SAN LUIS LA MANZANA");
        localities.put("0025", "SAN DIEGO DEL CERRITO 1RA SECC. (SAN DIEGO");
        localities.put("0031", "SAN MARCOS DE LA LOMA");
        localities.put("0033", "SANSON");
        localities.put("0035", "SANTIAGO DEL MONTE");
        localities.put("0067", "AGUA ZARCA");
        localities.put("0077", "SITIO CENTRO EL (EL SITIO)");
        localities.put("0108", "BARRIO DEL CERRILLO");
        localities.put("0114", "BARRIO DE SAN MIGUEL");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("115", "XONACATLAN");
        localities.put("0002", "DOLORES");
        localities.put("0004", "LOMA, LA");
        localities.put("0005", "SAN MIGUEL MIMIAPAN");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("116", "ZACAZONAPAN");
        localities.put("0001", "ZACAZONAPAN");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("118", "ZINACANTEPEC");
        localities.put("0001", "ZINACANTEPEC");
        localities.put("0053", "SAN ANTONIO ACAHUALCO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("120", "ZUMPANGO");
        localities.put("0001", "ZUMPANGO");
        localities.put("0004", "SAN MIGUEL BOCANEGRA");
        localities.put("0012", "SANTIAGO");
        localities.put("0019", "UNIDAD HABITACIONAL C.T.C.");
        localities.put("0034", "PRIMERO DE MAYO");
        localities.put("0045", "SAN JOSE DE LA LOMA");
        localities.put("0054", "SAN SEBASTIAN");
        localities.put("0155", "SAN LORENZO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("121", "CUAUTITLAN IZCALLI");
        localities.put("0001", "CUAUTITLAN IZCALLI");
        localities.put("0109", "EJIDO SAN MARTIN TEPETLIXPAN");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("122", "VALLE DE CHALCO SOLIDARIDAD");
        localities.put("0001", "VALLE DE CHALCO SOLIDARIDAD");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);
        town = new Town("124", "SAN JOSE DEL RINCON");
        localities.put("0053", "MESA LA");
        localities.put("0077", "SAN ANTONIO PUEBLO NUEVO");
        town.setLocalities(localities);
        town.setSections(generateSections());

        towns.add(town);

    }

    private static Map<String, String> generateSections() {
        int sections = randomGenerator.nextInt(2) + 6;
        Map<String, String> mapSections = new ArrayMap<>();
        for (int i = 0; i < sections; i++) {
            String section = String.valueOf(randomGenerator.nextInt(3000) + 6000);
            mapSections.put(section, section);
        }
        return mapSections;
    }

    public Town(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getLocalities() {
        return localities;
    }

    public void setLocalities(Map<String, String> localities) {
        this.localities = localities;
    }

    public Map<String, String> getSections() {
        return sections;
    }

    public void setSections(Map<String, String> sections) {
        this.sections = sections;
    }

    public static void setTowns(ArrayList<Town> towns) {
        Town.towns = towns;
    }

    public Random getRandomGenerator() {
        return randomGenerator;
    }

    public void setRandomGenerator(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }
}
