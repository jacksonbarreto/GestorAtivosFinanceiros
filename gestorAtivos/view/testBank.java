package view;


import model.*;

public class testBank {


    public static void main(String[] args) {



        /*
        User carlos = userDao.findByUsername("carlos").get(0);
        //carlos.removeAssetFinancial(1158L);
        System.out.println("id pego: " + carlos.getFinancialAssets().get(1).getId());
        RentalProperty propertyAA = (RentalProperty) carlos.getFinancialAssets().get(1);
        propertyAA.changeRentAmount(new BigDecimal("155.22"));
        System.out.println("==================================");
        System.out.println(carlos);
        System.out.println("===================================");
        List<FinancialAsset> ativos = carlos.findFinancialAsset(AssetType.PROPERTY);
        System.out.println("Total de Imóveis (3): " + ativos.size());
        ativos = carlos.findFinancialAsset(AssetType.FOUND);
        System.out.println("Total de Fundos (2): " + ativos.size());
        for (FinancialAsset fa : ativos) {
            System.out.println("#########pagamentos do Ativo###########");
            for (Payment p : fa.getPayments()) {
                System.out.println("Id do PAgamento: " + p.getId());
            }
            System.out.println("########################");
        }
        ativos = carlos.findFinancialAsset(AssetType.DEPOSIT);
        System.out.println("Total de Depósitos (0): " + ativos.size());
        System.out.println("Total de Logs (6): " + carlos.getLogs().size());
        System.out.println("-----Logs-----------");
        for (Log l : carlos.getLogs()) {
            System.out.println(l);
        }
        System.out.println("------fimLog-----");
        for (FinancialAsset fa : ativos) {

            System.out.println(fa.getAssetType() + " - " + fa.getDesignation());
            System.out.println("----pagamentos--------");
            for (Payment p : fa.getPayments()) {
                System.out.println(p);
            }
            System.out.println("-----------------");
        }


        //userDao.save(carlos);

         */
        LogSystem.record();

    }

}
