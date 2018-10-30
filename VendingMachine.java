import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class VendingMachine {
    //afiseaza meniul

    private VMType VmType;
    private Currency currency;
    private int numberOfProducts;
    private int numberOfCoins;
    private Map<Product, Integer> productStock;
    private Map<Coin, Integer> coinStock;

    //facem un constructor cu parametri - locatia fisierului de unde vrem sa citim
    public VendingMachine(String filePath) {
        this.initialize(filePath);
    }

    public void displayMenu() {
        System.out.println("This is a " + VmType + " vending machine");
        System.out.println(currency);
        System.out.println("Cod\tProdus\tPret\tGramaj");
        for (Product product : productStock.keySet()) {
            System.out.println(product.getCod() + "\t" + product.getName() + "\t" + product.getPrice() + "\t" + product.getSize());

        }
    }

    public void chooseCoins() {
        System.out.println("Alege bancnota cu care platesti. Tasteaza codul corespunzator si apoi tasteaza 0 pentru a finaliza plata");
        System.out.println("Cod\tBancnota");
        for (Coin coins : coinStock.keySet()) {
            System.out.println(coins.getIndex() + "\t" + coins.getCurrencyName() + "\t" + coins.getCoin());
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
        }

    }

    public void buyProduct() {
        System.out.println("Pentru a alege un produs, tasteaza codul corespunzator. Pentru a renunta, tasteaza 0");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        if (option == 0) {
            System.exit(0);
        }
        for (Product p : productStock.keySet()) {

            if (p.getCod() == option) {
                Integer quantity = productStock.get(p);
                if (quantity > 0) {
                    productStock.put(p, quantity - 1);
                    //Task 5: Se livreaza produsul utilizatorului si se afiseaza un mesaj corespunzator “Ati achizionat produsul: ceai”.
                    System.out.println("Ati achizitionat produsul: " + p.getName());
                } else {
                    System.out.println("Nu sunt produse suficiente");
                }

            }
        }
    }

    //porneste vending machine
    public void start() {
        while (true) {
            this.displayMenu();
            this.buyProduct();
            this.chooseCoins();
        }
    }


    //citim din fisier si luam prima linie care ne spune tipul produsului
    //scriem filePath ca sa nu hardcodam fisierul, asa incat sa setam fisierul dorit in Main
    public void initialize(String filePath) {
        //am cerut filepath
        Path path = Paths.get(filePath);
        //am initializat lista la null
        List<String> lines = null;
        //am citit toate liniile de cod si am
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        VmType = VMType.valueOf(lines.get(0));
        currency = Currency.valueOf(lines.get(1));
        numberOfProducts = Integer.valueOf(lines.get(2));
        productStock = new LinkedHashMap<>();
        coinStock = new HashMap<>();
        for (int i = 3; i < lines.size(); i++) {
            String line = lines.get( i );
            String[] parts = line.split( " " );
            if (Integer.valueOf( parts[0] ) < 100) {
                Product product = new Product( Integer.valueOf( parts[0] ), parts[1], Integer.valueOf( parts[2] ), Integer.valueOf( parts[3] ) );
                productStock.put( product, Integer.valueOf( parts[4] ) );
            } else if (Integer.valueOf(parts[0])>100){ ;
                    Coin coin = new Coin( Integer.valueOf( parts[0] ), parts[1], Integer.valueOf( parts[2] ) );
                    coinStock.put( coin,Integer.valueOf( parts[3] ) );
                }
            }
        }
    }

