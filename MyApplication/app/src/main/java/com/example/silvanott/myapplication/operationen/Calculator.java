package com.example.silvanott.myapplication.operationen;

import android.app.Activity;

import com.example.silvanott.myapplication.Advanced;
import com.example.silvanott.myapplication.MainActivity;
import com.example.silvanott.myapplication.sharepreferences.SharePreferences;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.io.IOError;
import java.util.EmptyStackException;

/**
 * Created by silvan.ott on 24.01.2017.
 */
public class Calculator {

    private String number = "0",calc = "", calculation = "";
    private boolean point = true,refresh = false,input = false;
    private String last = "=",number1,operator;

    public void calculate(){
        if(last.equals("=")){
            return ;
        }

        try {
            if(!input) {
                synchron(number);
            }

            while(test(calculation.charAt(calculation.length()-1)+"", "+-*/^√", false)){
                calculation = calculation.substring(0, calculation.length() - 1);
            }

            if(calculation.equals(".")){
                return ;
            }

            for(int i = 0;i<calculation.length();i++){
                if(calculation.charAt(i) == '!' || calculation.charAt(i) == '\u00B2'){
                    change(calculation.charAt(i),i);
                }
            }
            int open = 0,close = 0;
            for(int i = 0;i<calculation.length();i++){
                if(calculation.charAt(i) == '('){open++;}
                else if(calculation.charAt(i) == ')'){close++;}
            }
            if(open > close){
                for(int j = 0;j<open-close;j++){
                    calc += ")";
                    calculation += ")";
                }
            }else{
                for(int j = 0;j<close-open;j++){
                    calc = "(" + calc;
                    calculation = "(" + calculation;
                }
            }
            calc += "=";
            Expression e = new ExpressionBuilder(calculation).build();
            double result = e.evaluate();
            number = ""+result;
            if(number.endsWith(".0")){
                remove(2);
            }
            last = "=";
            point = true;
        }
        catch(Exception e){
            last = "=";
            point = true;
            number = "Error";
        }
    }

    public boolean getInput(){return input;}
    public String getLast(){return last;}
    public String getCalc(){return calc;}
    public String getNumber(){return number;}
    public String getOperator(){return operator;}

    public void add(String s){
        try{

            if((number.length()>=18) || last.equals("^2") && test(s,"+-*/",true)){
                return;
            }
            if(test(s,"+-/*",false) && test(last,"+-*/",false) || number.equals("0") && last.equals("0")){
                number = number.substring(0,number.length()-1);
            }


            if(last.equals("=")){
                synchron("");
            }

            if(test(last,"+-*/",false) && test(s,"+-*/",true)){
                refresh = true;
            }
            if(refresh){
                synchron(number);
                number = "";
                point = true;
                refresh = false;
                operator = "";
            }
            if(!point && s.equals(".")){
                return ;
            }
            if((number.equals("0") && last.equals("=") && !s.equals(".")) || last == "=" && test(s,"+-/*^π!",true)){
                number = "";
                synchron("");
            }
            if(number.equals("") && s.equals(".")){
                number += "0";
            }
            if(s.equals(".")){
                point = false;
            }
            switch(s){
                case "!": operator = "!"; break;
                case "^2": operator = "^2"; number += hochtief(true,"2"); last = "^2"; return;
            }
        number += s;
        last = s;
    }
    catch(Exception e){}
    }
    public void allClear(){
        synchron("");
        number = "0";
        point = true;
        last = "=";
        input = false;
    }

    public void remove(int i){
        if(last.equals("=")){
            number = "0";
            synchron("");
            return;
        }
        if(number.length()-i > 0) {
            if(number.substring(number.length() - 1).equals(".")){
                point = true;
            }
            number = number.substring(0, number.length() - i);
            last = number.substring(number.length() - 1);
        }else{
            number = "";
            last = "0";
        }
    }
    public boolean test(String value, String test, boolean all) {
        if (!all) {
            for (int i = 0; i < test.length(); i++) {
                if (test.charAt(i) == value.charAt(0)) {
                    return true;
                }
            }
            return false;
        } else {
            for (int i = 0; i < test.length(); i++) {
                if (test.charAt(i) == value.charAt(0)) {
                    return false;
                }
            }
            return true;
        }
    }

    public void change(char c, int i){
        String l = "",f = "";
        switch(c){
            case'!':
                String zahl = getZahl(i,false);
                int z = Integer.parseInt(zahl);
                if(z>25){throw new EmptyStackException();}
                long a = 1;
                while(z > 0){
                    a *= z;
                    z--;
                }
                l = calculation.substring(i+1,calculation.length());
                f = calculation.substring(0,i-zahl.length());
                calculation = f + a + l; break;
            case'\u00B2':
                l = calculation.substring(i+1,calculation.length());
                f = calculation.substring(0,i);
                calculation = f + "^2" + l;
        }
    }

    public String getZahl(int i,boolean up){
        String zahl = "",umdrehen = "";
        if(up){
            for(int j = i+1;j<calculation.length();j++){
                if(test(calculation.charAt(j)+"","0123456789",false)){
                    zahl += calculation.charAt(j);
                }else{break;}
            }
        }
        else{
            for(int j = i-1;j>=0;j--){
                if(test(calculation.charAt(j)+"","0123456789",false)){
                    zahl += calculation.charAt(j);
                }else{break;}
            }
            for(int k = zahl.length()-1;k>=0;k--){
                umdrehen += zahl.charAt(k);
            }
            zahl = umdrehen;
        }
        return zahl;
    }

    public String hochtief(boolean hoch,String zahl){
        if(hoch){
            switch (zahl){
                case "0": return "\u2070";
                case "1": return "\u00B9";
                case "2": return "\u00B2";
                case "3": return "\u00B3";
                case "4": return "\u2074";
                case "5": return "\u2075";
                case "6": return "\u2076";
                case "7": return "\u2077";
                case "8": return "\u2078";
                case "9": return "\u2079";
            }
        }
        else{
            switch (zahl){
                case "0": return "\u2080";
                case "1": return "\u2081";
                case "2": return "\u2082";
                case "3": return "\u2083";
                case "4": return "\u2084";
                case "5": return "\u2085";
                case "6": return "\u2086";
                case "7": return "\u2087";
                case "8": return "\u2088";
                case "9": return "\u2089";
            }
        }
        return zahl;
    }

    public void negate(){
        if(number.charAt(0) == '-'){
            number = number.substring(1,number.length());}
        else{number = "-" + number;}
    }

    public void synchron(String add){
        if (add.equals("")){
            calculation = "";
            calc = "";
            input = false;
        }
        calc += add;
        calculation += add;
    }
    public void advanced(String value){

        if(last.equals("=")){
            synchron("");
        }
        if(input){
            switch(operator){
                case "^": calc += number1;
                    for(int i = 0;i<number.length();i++){
                        calc += hochtief(true,number.charAt(i)+"");
                    }
                    calculation += number1 + "^" + number;
                    break;
                case "√":
                    if(!value.equals("=")) {
                        for (int i = 0; i < number.length() - 1; i++) {
                            calc += hochtief(true, number.charAt(i) + "");
                        }
                        calc += "√" + number1 + number.charAt(number.length() - 1);
                        calculation += "pow(" + number1 + ",1/" + number.substring(0, number.length() - 1) + ")" + number.charAt(number.length() - 1);
                    }
                    else{
                        for (int i = 0; i < number.length(); i++) {
                            calc += hochtief(true, number.charAt(i) + "");
                        }
                        calc += "√" + number1;
                        calculation += "pow(" + number1 + ",1/" + number.substring(0, number.length()) + ")";
                    }
                    break;
            }
            if(value.equals("=")){
                calculate();
            }
            else {
                number = value;
                last = value;
            }
            input = false;
        }
        else{
            number1 = number;
            number = "";
            input = true;
            operator = value;
            last= value;
        }


    }
}
