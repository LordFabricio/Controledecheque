/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author LordFabricio
 */
public class Principal {

    public Scanner ent;
    private double caixa;
    private List<Cheque> cheques;
    private Random r;

    public Principal() {
        cheques = new ArrayList<>();
        ent = new Scanner(System.in);
        caixa = 10000.00;
        r = new Random();
    }

    public void start() {
        int op;
        do {
            System.out.println("= Controle de Cheques =");
            System.out.println("Passar Cheque.....: (1)");
            System.out.println("Listar Cheques....: (2)");
            System.out.println("Sustar Cheques....: (3)");
            System.out.println("Verificar Caixa...: (4)");
            System.out.println("Finalizar.........: (5)");
            System.out.println("========Opções=========");
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
                    System.out.println("======Sustar Cheque======");
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
                        } else if (chk.getStatsc().equals("Sustado")) {
                            System.out.println("Cheque Já foi Sustado");
                        } else {
                            chk.setStatsc("Sustado");
                            System.out.printf("Nº cheque.: %s - Valor Cheque.: R$ %.2f - Data.: %s - Situação.: %s\n", chk.getNcheque(), chk.getVcheque(), chk.getDatec(), chk.getStatsc());
                            System.out.println("Cheque Sustado");
                            caixa = saldo;
                        }
                        ent.nextLine();
                        System.out.print("Continuar Sustando ? (1)sim / (2)não ");
                        sair = ent.nextInt();
                        if (sair == 1){
                            continue;
                        } else {
                            break;
                        }
                    } while (sair != 2);
                    continue;
                case 4:
                    System.out.println();
                    System.out.printf("Saldo da Conta.: R$ %.2f\n", caixa);
                    System.out.println();
                    continue;
                case 5:
                    break;
                default:
                    System.out.println("Opção Inválida");
            }

        } while (op != 5);
    }

    public void cadCHK() {
        int ncheque;
        LocalDate datec;
        double vcheque;
        String statsc;
        Cheque chk = new Cheque();
        System.out.println("====Passar Cheque====");
        ent.nextLine();
        System.out.println();
        ncheque = 100000 + r.nextInt(899999);
        System.out.printf("Numero do Cheque.: %s\n", ncheque);
        if (insertCad(ncheque)) {
            System.out.println();
            System.out.println("Cheque Já Cadastrado");
            System.out.println("Cadastro Cancelado");
            System.out.println();
        } else {
            System.out.print("Valor do Cheque.......: ");
            vcheque = ent.nextDouble();
            datec = LocalDate.now();
            System.out.printf("Data de Lançamento..: %s\n", datec);
            statsc = "Ncompensado";
            System.out.printf("Situação..: %s\n", statsc);
            chk.cadCheque(ncheque, vcheque, datec, statsc);
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
        if (cheques.isEmpty()) {
            System.out.println("Nenhum Cheque Cadastros");
        } else {
            int index = 1;
            for (Cheque chk : cheques) {
                System.out.printf("[%d] Nº Cheque.: %s - Valor.: R$ %.2f - Data.: %s - Situação.: %s\n", index, chk.getNcheque(), chk.getVcheque(), chk.getDatec(), chk.getStatsc());
                index++;
            }
            System.out.println();
        }
    }
}
