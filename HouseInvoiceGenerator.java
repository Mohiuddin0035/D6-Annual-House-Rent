import java.util.*;

public class HouseInvoiceGenerator {

    static final int SERVICE_CHARGE = 500;
    static final int GARBAGE_BILL = 150;
    static final int WIFI_BILL = 2000;
    static final int Bua_Payment = 4000;
    static final int TOTAL_MEMBERS = 6;
    static final double BKASH_CHARGE_PER_1K = 19.0;

    static final String[] members = {"Zurat", "Saber", "Soikot", "Shrabon", "Zarif", "Shuvo"};

    static final Map<String, Integer> baseRent = new HashMap<>() {{
        put("Zurat", 3500);
        put("Saber", 3500);
        put("Soikot", 3500);
        put("Shrabon", 3500);
        put("Zarif", 3000);
        put("Shuvo", 3000);
    }};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, Double> advancePaid = new HashMap<>();
        Map<String, Double> individualDue = new HashMap<>();
        Map<String, Double> finalDue = new HashMap<>();

        System.out.print("Enter month and year (e.g., May 2025): ");
        String month = sc.nextLine();

        System.out.print("Enter electricity bill amount: ");
        double electricity = sc.nextDouble();

        double totalCost = electricity + SERVICE_CHARGE + GARBAGE_BILL + WIFI_BILL + Bua_Payment;
        double perPersonShare = totalCost / TOTAL_MEMBERS;

        System.out.println("\nEnter advance payment (if none, type 0):");
        for (String m : members) {
            System.out.print(m + ": ");
            advancePaid.put(m, sc.nextDouble());
        }

        sc.nextLine(); // consume newline
        System.out.print("\nPayment mode (bkash/cash): ");
        String paymentMode = sc.nextLine().trim().toLowerCase();

        double totalCollectable = 0;

        for (String m : members) {
            double due = perPersonShare + baseRent.get(m) - advancePaid.get(m);
            individualDue.put(m, due);

            double bkashCharge = 0;
            if (paymentMode.equals("bkash")) {
                bkashCharge = (due / 1000.0) * BKASH_CHARGE_PER_1K;
            }

            double totalDue = due + bkashCharge;
            finalDue.put(m, totalDue);
            totalCollectable += totalDue;
        }

        // -------- Print Invoice --------
        System.out.println("\n\n🧾 INVOICE: House Rent & Utilities – " + month);
        System.out.println("-------------------------------------------------");
        System.out.println(String.format("%-30s %s", "Description", "Amount (৳)"));
        System.out.println("-------------------------------------------------");
        System.out.println(String.format("%-30s %.2f", "Electricity Bill", electricity));
        System.out.println(String.format("%-30s %d", "Service Charge", SERVICE_CHARGE));
        System.out.println(String.format("%-30s %d", "Garbage Bill", GARBAGE_BILL));
        System.out.println(String.format("%-30s %d", "WiFi Bill", WIFI_BILL));
        System.out.println(String.format("%-30s %d", "Bua Payment", WIFI_BILL));
        System.out.println(String.format("%-30s %.2f", "Total", totalCost));
        System.out.println(String.format("%-30s %d", "Total Members", TOTAL_MEMBERS));
        System.out.println(String.format("%-30s %.2f", "Per Person Share", perPersonShare));

        System.out.println("\n💰 Paid Adjustment:");
        for (String m : members) {
            if (advancePaid.get(m) > 0) {
                System.out.println(m + " (Advance): -" + advancePaid.get(m));
            }
        }

        System.out.println("\n📌 Individual Dues (Before bKash Charge):");
        for (String m : members) {
            System.out.println(m + " = " + String.format("%.2f", individualDue.get(m)));
        }

        if (paymentMode.equals("bkash")) {
            System.out.println("\n💳 Final Payable with bKash Cashout (19tk/1k):");
            for (String m : members) {
                double bkash = finalDue.get(m) - individualDue.get(m);
                System.out.println(m + " = " + String.format("%.2f", individualDue.get(m)) +
                        " + " + String.format("%.2f", bkash) +
                        " = ৳" + String.format("%.2f", finalDue.get(m)));
            }
        } else {
            System.out.println("\n💵 Final Payable (Cash):");
            for (String m : members) {
                System.out.println(m + " = ৳" + String.format("%.2f", finalDue.get(m)));
            }
        }

        System.out.println("\n-------------------------------------------------");
        System.out.println("🧾 Total Collectable Amount: ৳" + String.format("%.2f", totalCollectable));
        System.out.println("✅ Everyone is requested to pay their respective amount.");
    }
}
