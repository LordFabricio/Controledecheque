/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author LordFabricio
 */
public class Principal {

    public Scanner ent;
    private double caixa;
    private List<Cheque> cheques;
    private Random r;
    private DateTimeFormatter dataformat;

    public Principal() {
        cheques = new ArrayList<>();
        ent = new Scanner(System.in);
        caixa = 10000.00;
        r = new Random();
        dataformat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    private void autocheque() {
        int ncheque;
        int n = 0;
        LocalDate datec;
        double vcheque;
        String statsc;
        Cheque chk = new Cheque();
        ncheque = 100000 + r.nextInt(899999);
        vcheque = vrandom();
        datec = data();
        statsc = renum();
        if (statsc.equalsIgnoreCase("Compensado") && caixa > vcheque) {
            caixa = caixa - vcheque;
        } else if (caixa < vcheque) {
            statsc = "SemFundo";
        }
        chk.cadCheque(ncheque, vcheque, datec, statsc);
        cheques.add(chk);
    }

    public void start() {
        int op;
        int auto10 = 0;
        System.out.println("O Sistema Criou 10 Cheques Aleatórios");
        do {
            auto10++;
            if (auto10 < 11) {
                autocheque();
            } else {
                break;
            }
        } while (true);
        do {
            System.out.println("== Controle de Cheques ===");
            System.out.println("Passar Cheque........: (1)");
            System.out.println("Listar Cheques.......: (2)");
            System.out.println("Cheques por Situação.: (3)");
            System.out.println("Compensar Cheques....: (4)");
            System.out.println("Verificar Caixa......: (5)");
            System.out.println("Finalizar............: (6)");
            System.out.println("========= Opções =========");
            System.out.println();
            System.out.print("Escolha a opção...: ");
            op = ent.nextInt();
            switch (op) {
                case 1:
                    cadCHK();
                    continue;
                case 2:
                    listCHK();
                    continue;
                case 3:
                    listCHKS();
                    continue;
                case 4:
                    System.out.println("====Compensar Cheque====");
                    System.out.println();
                    listCHK();
                    double saldo;
                    saldo = caixa;
                    int cod;
                    int sair;
                    do {
                        System.out.print("Codigo do Cheque.: ");
                        cod = ent.nextInt();
                        Cheque chk = cheques.get(cod - 1);
                        saldo = saldo - chk.getVcheque();
                        if (saldo < 0) {
                            System.out.println("Saldo Indisponivel");
                            chk.setStatsc("SemFundo");
                        } else if (chk.getStatsc().equals("Compensado")) {
                            System.out.println("Cheque Já foi Compensado");
                        } else {
                            chk.setStatsc("Compensado");
                            System.out.printf("Nº cheque.: %s - Valor Cheque.: R$ %.2f - Data.: %s - Situação.: %s\n", chk.getNcheque(), chk.getVcheque(), chk.getDatec(), chk.getStatsc());
                            System.out.println("Cheque Compensado");
                            caixa = saldo;
                        }
                        ent.nextLine();
                        System.out.print("Continuar Sustando ? (1)sim / (2)não ");
                        sair = ent.nextInt();
                        if (sair == 1) {
                            continue;
                        } else {
                            break;
                        }
                    } while (sair != 2);
                    continue;
                case 5:
                    System.out.println();
                    System.out.printf("Saldo da Conta.: R$ %.2f\n", caixa);
                    System.out.println();
                    continue;
                case 6:
                    break;
                default:
                    System.out.println("Opção Inválida");
            }

        } while (op != 6);
    }

    public void cadCHK() {
        int ncheque;
        String datec;
        LocalDate convert;
        double vcheque;
        String statsc;
        Cheque chk = new Cheque();
        System.out.println("====Passar Cheque====");
        ent.nextLine();
        System.out.println();
        System.out.print("Numero do Cheque.......: ");
        ncheque = ent.nextInt();
        if (insertCad(ncheque)) {
            System.out.println();
            System.out.println("Cheque Já Cadastrado");
            System.out.println("Cadastro Cancelado");
            System.out.println();
        } else {
            System.out.print("Valor do Cheque.......: ");
            vcheque = ent.nextDouble();
            ent.nextLine();
            System.out.print("Data do Cheque......: ");
            datec = ent.nextLine();
            convert = LocalDate.parse(datec, dataformat);
            statsc = renum();
            if (statsc.equalsIgnoreCase("Compensado") && caixa > vcheque) {
                caixa = caixa - vcheque;
            } else if (caixa < vcheque) {
                statsc = "SemFundo";
            }
            System.out.printf("Situação..: %s\n", statsc);
            chk.cadCheque(ncheque, vcheque, convert, statsc);
            System.out.println();
            System.out.println("Cheque Cadastrado");
            cheques.add(chk);
        }

    }

    public boolean insertCad(int ncheque) {
        for (Cheque chk : cheques) {
            if (ncheque == chk.getNcheque()) {
                return true;
            }
        }
        return false;
    }

    public void listCHK() {
        double comp = 0.00;
        double aberto = 0.00;
        double semf = 0.00;
        String formatadata;
        if (cheques.isEmpty()) {
            System.out.println("Nenhum Cheque Cadastros");
        } else {
            int index = 1;
            for (Cheque chk : cheques) {
                formatadata = String.valueOf(chk.getDatec().format(dataformat));
                System.out.printf("[%d] Nº Cheque.: %s - Valor.: R$ %.2f - Data.: %s - Situação.: %s\n", index, chk.getNcheque(), chk.getVcheque(), formatadata, chk.getStatsc());
                if (chk.getStatsc().equalsIgnoreCase("Aberto")) {
                    aberto = aberto + chk.getVcheque();
                } else if (chk.getStatsc().equalsIgnoreCase("Compensado")) {
                    comp = comp + chk.getVcheque();
                } else if (chk.getStatsc().equalsIgnoreCase("SemFundo")) {
                    semf = semf + chk.getVcheque();
                }
                index++;
            }
            System.out.println();
            System.out.printf("== Cheques em Aberto...: R$ %.2f - "
                    + "Cheques Compensados.: R$ %.2f - "
                    + "Cheques Sem Fundo...: R$ %.2f ==\n", aberto, comp, semf);
            System.out.println();
        }
    }

    public void listCHKS() {
        double comp = 0.00;
        double aberto = 0.00;
        double semf = 0.00;
        String formatadata;
        int status = 0;
        if (cheques.isEmpty()) {
            System.out.println("Nenhum Cheque Cadastros");
        } else {
            System.out.print("Escolha uma Opção (1)Aberto - (2)Compensado - (3)Sem Fundo .: ");
            status = ent.nextInt();
            if (status == 1) {
                int index = 1;
                for (Cheque chk : cheques) {
                    formatadata = String.valueOf(chk.getDatec().format(dataformat));
                    if (chk.getStatsc().equalsIgnoreCase("Aberto")) {
                        System.out.printf("[%d] Nº Cheque.: %s - Valor.: R$ %.2f - Data.: %s - Situação.: %s\n", index, chk.getNcheque(), chk.getVcheque(), formatadata, chk.getStatsc());
                        aberto = aberto + chk.getVcheque();
                    }
                    index++;
                }
                System.out.println();
                System.out.printf("== Cheques em Aberto...: R$ %.2f ==\n", aberto);
                System.out.println();
            } else if (status == 2) {
                int index = 1;
                for (Cheque chk : cheques) {
                    formatadata = String.valueOf(chk.getDatec().format(dataformat));
                    if (chk.getStatsc().equalsIgnoreCase("Compensado")) {
                        System.out.printf("[%d] Nº Cheque.: %s - Valor.: R$ %.2f - Data.: %s - Situação.: %s\n", index, chk.getNcheque(), chk.getVcheque(), formatadata, chk.getStatsc());
                        comp = comp + chk.getVcheque();
                    }
                    index++;
                }
                System.out.println();
                System.out.printf("== Cheques Compensados.: R$ %.2f ==\n", comp);
                System.out.println();
            } else if (status == 3) {
                int index = 1;
                for (Cheque chk : cheques) {
                    formatadata = String.valueOf(chk.getDatec().format(dataformat));
                    if (chk.getStatsc().equalsIgnoreCase("SemFundo")) {
                        System.out.printf("[%d] Nº Cheque.: %s - Valor.: R$ %.2f - Data.: %s - Situação.: %s\n", index, chk.getNcheque(), chk.getVcheque(), formatadata, chk.getStatsc());
                        semf = semf + chk.getVcheque();
                    }
                    index++;
                }
                System.out.println();
                System.out.printf("== Cheques Sem Fundo..: R$ %.2f ==\n", semf);
                System.out.println();
            }
        }
    }

    public static LocalDate data() {
        LocalDate data;
        long minDay = LocalDate.of(2017, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2017, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        data = randomDate;
        return data;
    }

    public static double vrandom() {
        double vrandom;
        double inicio = 100;
        double fim = 2000;
        double random = new Random().nextDouble();
        double result = inicio + (random * (fim - inicio));
        vrandom = result;
        return vrandom;
    }

    public String renum() {
        int tenum = 1 + r.nextInt(2);
        String renum;
        switch (tenum) {
            case 1:
                renum = String.valueOf(Statusenum.Aberto);
                break;
            case 2:
                renum = String.valueOf(Statusenum.Compensado);
                break;
            case 3:
                renum = String.valueOf(Statusenum.SemFundo);
                break;
            default:
                renum = String.valueOf(Statusenum.Aberto);
                break;
        }
        return renum;
    }
}
