import java.io.*;

class BullyAlgo {
    int cood, ch, crash;
    int[] prc;

    public void election(int n) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nThe Coordinator Has Crashed!");

        int flag = 1;

        while (flag == 1) {
            crash = 0;

            for (int i = 0; i < n; i++) {
                if (prc[i] == 0)
                    crash++;
            }

            if (crash == n) {
                System.out.println("\n*** All Processes Are Crashed ***");
                break;
            } else {
                System.out.println("\nEnter The Initiator:");
                int init = Integer.parseInt(br.readLine());

                if ((init < 1) || (init > n) || (prc[init - 1] == 0)) {
                    System.out.println("\nInvalid Initiator");
                    continue;
                }

                for (int i = init - 1; i < n; i++)
                    System.out.println("Process " + (i + 1) + " Called For Election");

                System.out.println("");
                for (int i = init - 1; i < n; i++) {
                    if (prc[i] == 0)
                        System.out.println("Process " + (i + 1) + " Is Dead");
                    else
                        System.out.println("Process " + (i + 1) + " Is In");
                }

                for (int i = n - 1; i >= 0; i--) {
                    if (prc[i] == 1) {
                        cood = (i + 1);
                        System.out.println("\n*** New Coordinator Is " + cood + " ***");
                        flag = 0;
                        break;
                    }
                }
            }
        }
    }

    public void Bully() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter The Number Of Processes: ");
        int n = Integer.parseInt(br.readLine());
        prc = new int[n];
        crash = 0;

        for (int i = 0; i < n; i++)
            prc[i] = 1;

        cood = n;

        do {
            System.out.println("\n\t1. Crash A Process");
            System.out.println("\t2. Recover A Process");
            System.out.println("\t3. Display New Coordinator");
            System.out.println("\t4. Exit");
            System.out.print("Enter your choice: ");

            ch = Integer.parseInt(br.readLine());

            switch (ch) {
                case 1:
                    System.out.print("\nEnter A Process To Crash: ");
                    int cp = Integer.parseInt(br.readLine());

                    if ((cp > n) || (cp < 1)) {
                        System.out.println("Invalid Process! Enter A Valid Process");
                    } else if ((prc[cp - 1] == 1) && (cood != cp)) {
                        prc[cp - 1] = 0;
                        System.out.println("\nProcess " + cp + " Has Been Crashed");
                    } else if ((prc[cp - 1] == 1) && (cood == cp)) {
                        prc[cp - 1] = 0;
                        election(n);
                    } else {
                        System.out.println("\nProcess " + cp + " Is Already Crashed");
                    }
                    break;

                case 2:
                    crash = 0;
                    System.out.println("\nCrashed Processes Are: ");
                    for (int i = 0; i < n; i++) {
                        if (prc[i] == 0) {
                            System.out.println((i + 1));
                            crash++;
                        }
                    }

                    System.out.print("Enter The Process You Want To Recover: ");
                    int rp = Integer.parseInt(br.readLine());

                    if ((rp < 1) || (rp > n)) {
                        System.out.println("\nInvalid Process. Enter A Valid ID");
                    } else if ((prc[rp - 1] == 0) && (rp > cood)) {
                        prc[rp - 1] = 1;
                        System.out.println("\nProcess " + rp + " Has Recovered");
                        cood = rp;
                        System.out.println("Process " + rp + " Is The New Coordinator");
                    } else if (crash == n) {
                        prc[rp - 1] = 1;
                        cood = rp;
                        crash--;
                        System.out.println("Process " + rp + " Is The New Coordinator");
                    } else if ((prc[rp - 1] == 0) && (rp < cood)) {
                        prc[rp - 1] = 1;
                        System.out.println("\nProcess " + rp + " Has Recovered");
                    } else {
                        System.out.println("\nProcess " + rp + " Is Not A Crashed Process");
                    }
                    break;

                case 3:
                    System.out.println("\nCurrent Coordinator Is " + cood);
                    break;

                case 4:
                    System.exit(0);
                    break;

                default:
                    System.out.println("\nInvalid Entry!");
                    break;
            }

        } while (ch != 4);
    }

    public static void main(String args[]) throws IOException {
        BullyAlgo ob = new BullyAlgo();
        ob.Bully();
    }
}
