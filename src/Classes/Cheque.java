/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.time.LocalDate;

/**
 *
 * @author ViniciusVonAhn
 */
public class Cheque {
    
    private int ncheque;
    private LocalDate datec; //teste
    private double vcheque;
    private Statusenum statsc;

    public void cadCheque(int ncheque, double vcheque, LocalDate datec, Statusenum statsc) {
        setNcheque(ncheque);
        setVcheque(vcheque);
        setDatec(datec);
        setStatsc(statsc);
    }

    /**
     * @return the ncheque
     */
    public int getNcheque() {
        return ncheque;
    }

    /**
     * @return the datec
     */
    public LocalDate getDatec() {
        return datec;
    }

    /**
     * @return the vcheque
     */
    public double getVcheque() {
        return vcheque;
    }

    /**
     * @return the statsc
     */
    public Statusenum getStatsc() {
        return statsc;
    }

    /**
     * @param ncheque the ncheque to set
     */
    public void setNcheque(int ncheque) {
        this.ncheque = ncheque;
    }

    /**
     * @param datec the datec to set
     */
    public void setDatec(LocalDate datec) {
        this.datec = datec;
    }

    /**
     * @param vcheque the vcheque to set
     */
    public void setVcheque(double vcheque) {
        this.vcheque = vcheque;
    }

    /**
     * @param statsc the statsc to set
     */
    public void setStatsc(Statusenum statsc) {
        this.statsc = statsc;
    }

    @Override
    public String toString() {
        return "Cheque{" + "ncheque=" + ncheque + ", datec=" + datec + ", vcheque=" + vcheque + ", statsc=" + statsc + '}';
    }
}
