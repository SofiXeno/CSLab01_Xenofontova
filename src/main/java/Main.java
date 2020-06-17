import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
//        Sender client = new Sender();
//        User u = new User();
//        client.setUser(u);
//        Receiver r = new Receiver();
//        r.receiveMessage(client.sendMessage("Hello from Sofia Xenofontova"));
//        User u2 = new User();
//        client.setUser(u2);
//        r.receiveMessage(client.sendMessage("Hello from this test"));


//        ExecutorService executorService = Executors.newFixedThreadPool(18);
//
//        String msg = "hello";
//        String encryptedMsg = Encryptor.encrypt(msg); //зашифрувати повідомлення
//
//        System.out.println("Encrypted message: " + encryptedMsg);
//
//
//        for (int i = 0; i < 10; i++)
//            executorService.submit(() -> {
//                TCPNetwork tcpNetwork = new TCPNetwork();
//                tcpNetwork.receiveMessage(encryptedMsg);
//
//            });
//
//
//        try {
//            executorService.shutdown();
//            while (!executorService.awaitTermination(24L, TimeUnit.HOURS))
//                System.out.println("Not yet. Still waiting for termination MAIN");
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        Processor.shutdown(24L,TimeUnit.HOURS);
//        System.out.println("Tasks gone!");
//
//        System.out.println("Decrypted message: " + Encryptor.decrypt(encryptedMsg));//розшифрувати повідомлення


        System.out.println("CREATE + READ");
        final DAOProduct daoProduct = new DAOProduct("file.db");
        daoProduct.deleteAll();
        Product pr = new Product("product 1", 3.0, 2);
       int newProductID = daoProduct.insertProduct(pr);
        System.out.println(daoProduct.getList(0,9));
        System.out.println(daoProduct.getList(1,9));


//        ProductFilter productFilter = new ProductFilter();
//        productFilter.setFromPrice(0.0);
//        productFilter.setToPrice(1000.0);
//        System.out.println(daoProduct.getProductList(0,4, productFilter));


       // System.out.println(daoProduct.getProductList(1,2,));






        System.out.println("UPDATE");
        Product product = new Product("AAAAAA",120.0989,23);
        daoProduct.updateTable(4,product);
        System.out.println(daoProduct.getList(0,9));



        System.out.println("DELETE");
        daoProduct.deleteProduct(1);
        daoProduct.deleteProduct(2);
        System.out.println(daoProduct.getList(0,9));
//
//
//
//
//
//        System.out.println("\nLIST BY CRITERIA");
//
//        for (int i = 1; i < 20; i++) {
//
//            daoProduct.insertProduct(new Product("Product" + i, Math.random() * 10, (int) Math.random() * 10));
//        }
//
//
//        daoProduct.getProductList(0, 200, new ProductFilter()).forEach(System.out::println);
//        System.out.println("========");
//
//
//        final ProductFilter filter = new ProductFilter();
//        filter.setIds(Arrays.asList(1, 2, 10, 15));
//        filter.setFromPrice(250.0);
//
//        daoProduct.getProductList(0, 200, filter).forEach(System.out::println);

    }

}


