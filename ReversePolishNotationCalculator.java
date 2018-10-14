import java.io.*;
import java.util.*;
import java.util.Stack;
import java.lang.*;

import java.lang.Math;;


class ReversePolishNotationCalculator{


    public static void main(String[] args){
        
        Queue<String> infixQ = new LinkedList<String>();
        Stack<Character> opStack = new Stack<Character>();
        Queue<String> postFixQ = new LinkedList<String>();
        Stack<Double> evalStack = new Stack<Double>();
        ReversePolishNotationCalculator r = new ReversePolishNotationCalculator();

        while(true){
            try {
                r.takeInputs(infixQ, opStack, postFixQ, evalStack);
                
            } catch (Exception e) {
                System.out.println("Invalid Mathematical Expression");
                while(!infixQ.isEmpty()){
                    infixQ.remove();
                }
                
            }
            
        }

    }

    public void takeInputs(Queue<String> infixQ, Stack<Character> opStack, Queue<String> postFixQ,Stack<Double> evalStack){
        Scanner sc = new Scanner(System.in);
        Stack<Character> braceOpen = new Stack<Character>();
        Stack<Character> braceClose = new Stack<Character>();
        String temp = "";
        System.out.print("Enter the expression or 'quit' to quit: ");
        String[] infixExpArr = sc.nextLine().split(" ");
        String infixExp ="";
        int decimalCounter=0;
        int counterOperator = 0;
        for(int x=0; x<infixExpArr.length; x++){
            infixExp +=infixExpArr[x];
        }
        if(infixExp.equals("quit")){
                System.exit(0);
        }
        for(int i = 0; i<infixExp.length();i++){
            char c = infixExp.charAt(i);
            
            if((i==0 && c == '-') || (i > 0 && c == '-' && infixExp.charAt(i-1) == '(') || (i!=0 && i<infixExp.length()-1 && c == '-' && !Character.isDigit(infixExp.charAt(i+1)) && !Character.isDigit(infixExp.charAt(i-1)))){
                System.out.println("Invalid Mathematical Expression");
                while(!infixQ.isEmpty()){
                    infixQ.remove();
                }
                while(true){
                    takeInputs(infixQ, opStack, postFixQ, evalStack);
                }
            }
            if(!Character.isDigit(infixExp.charAt(infixExp.length()-1)) && infixExp.charAt(infixExp.length()-1) != ')'){
                System.out.println("Invalid Mathematical Expression");
                while(!infixQ.isEmpty()){
                    infixQ.remove();
                }
                while(true){
                    takeInputs(infixQ, opStack, postFixQ, evalStack);
                }
            }

            if(Character.isDigit(c) || c == '.'){
                if(c == '.'){
                    decimalCounter++;
                }

                if(decimalCounter==1 && c == '.' && Character.isDigit(infixExp.charAt(i+1))){
                    temp +=c;
                }
                else if(decimalCounter>1 && c =='.'){
                    System.out.println("Invalid Mathematical Expression");
                    while(!infixQ.isEmpty()){
                        infixQ.remove();
                    }
                    while(true){
                        takeInputs(infixQ, opStack, postFixQ, evalStack);
                    }
                }
                else{
                    temp +=c;
                    counterOperator = 0;
                    if(i==infixExp.length()-1){
                        infixQ.offer(temp);
                        temp = "";
                        
                    }
                
                }
                
    
            }
            else{
                
                if(!temp.equals("")){
                    infixQ.offer(temp);
                    decimalCounter=0;
                    temp = "";
                }
                if((c=='+' || c=='-' || c=='*' || c=='/' || c == '^') && (counterOperator<1))
                {
                    counterOperator++;
                    String s = Character.toString(c);
                    infixQ.offer(s);
                }
                else if( c =='(' || c == ')' ){
                    
                    if(c == '('){
                        braceOpen.push(c);
                        String s = Character.toString(c);
                        infixQ.offer(s);    
                    }
                    if(c ==')'){
                        braceClose.push(c);
                        String s = Character.toString(c);
                        infixQ.offer(s); 
                    }

                    String s = Character.toString(c);
                    infixQ.offer(s);
                }
                else if(i>0 && (c =='p' || c == 'P') && (Character.isDigit(infixExp.charAt(i-1)) || infixExp.charAt(i-1) == ')')){
                    if(infixExp.charAt(i+1) == 'o' || infixExp.charAt(i+1) == 'O'){
                        if(infixExp.charAt(i+2) == 'w' || infixExp.charAt(i+2) == 'W'){
                            infixQ.offer("^");
                            i = i+2;
                            counterOperator++;
                        }
                        else{
                            System.out.println("Invalid Mathematical Expression");
                            while(!infixQ.isEmpty()){
                                infixQ.remove();
                            }
                            while(true){
                                takeInputs(infixQ, opStack, postFixQ, evalStack);
                            }
                        }
                    }
                    else{
                        System.out.println("Invalid Mathematical Expression");
                        while(!infixQ.isEmpty()){
                            infixQ.remove();
                        }
                        while(true){
                            takeInputs(infixQ, opStack, postFixQ, evalStack);
                        }
                    }
                }
                else{
                    System.out.println("Invalid Mathematical Expression");
                    while(!infixQ.isEmpty()){
                        infixQ.remove();
                    }
                    while(true){
                        takeInputs(infixQ, opStack, postFixQ, evalStack);
                    }
                }
            }
        }
        int braceCounterOpen =0;
        int braceCounterClose =0;
        while(!braceOpen.isEmpty()){
            braceOpen.pop();
            braceCounterOpen ++;
        }
        while(!braceClose.isEmpty()){
            braceClose.pop();
            braceCounterClose ++;
        }

        if(braceCounterOpen != braceCounterClose){
            System.out.println("Invalid Mathematical Expression");
                    while(!infixQ.isEmpty()){
                        infixQ.remove();
                    }
                    while(true){
                        takeInputs(infixQ, opStack, postFixQ, evalStack);
                    }
        }
        String outInfix ="";
        for(int infix =0; infix<infixQ.size();infix++){
            String teeemp = infixQ.peek();
            outInfix += infixQ.peek();
            outInfix += " ";
            infixQ.remove();
            infixQ.offer(teeemp);
        }
        System.out.println("The Infix Expression: " + outInfix);

        infixToPostfix(infixQ,opStack,postFixQ);
        String outPostfixValue = "";
        for(int outPostfix = 0; outPostfix<postFixQ.size(); outPostfix++){
            String teeemp2 = postFixQ.peek();
            outPostfixValue += postFixQ.peek();
            outPostfixValue += " ";
            postFixQ.remove();
            postFixQ.offer(teeemp2);
        }
        System.out.println("The PostFix Expression: " + outPostfixValue);
        calculate(postFixQ, evalStack);
        double value = evalStack.peek();
        System.out.printf("The result is: %.2f \n", value); //https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
    }


    public int precedence(char operator){
        if(operator == '+' || operator == '-'){
            return 1;
        } 
        else if(operator == '*' || operator == '/'){
            return 2;
        }
        else if(operator == '^')
        {
            return 3;
        }
        return -1;
    }

    public void infixToPostfix(Queue<String> infixQ, Stack<Character> opsStack, Queue<String> postFixQ){
        String t;
        char tt;
        while(!infixQ.isEmpty()){
            t=infixQ.peek();
            infixQ.remove();
            boolean numeric = true;
            try {                                       //https://www.programiz.com/java-programming/examples/check-string-numeric
                int num = Integer.parseInt(t);
            } catch (NumberFormatException e) {
                numeric = false;
            }
            Boolean DoubleNumber = true;
            try {
                Double fl = Double.parseDouble(t);
            } catch (NumberFormatException e) {
                DoubleNumber = false;
            }

            if(numeric){
                postFixQ.offer(t);
                
            }
            else if(DoubleNumber){
                postFixQ.offer(t);
            }
            else if(opsStack.isEmpty()){
                
                opsStack.push(t.charAt(0));
            }
            else if(t.charAt(0) == '('){
                opsStack.push(t.charAt(0));
            }
            else if (t.charAt(0) == ')'){
                while(opsStack.peek() != '('){
                    postFixQ.offer(opsStack.peek().toString());
                    opsStack.pop();
                }
                opsStack.pop();
            }
            else{
                while(!opsStack.isEmpty() && precedence(t.charAt(0)) <= precedence(opsStack.peek())){
                    postFixQ.offer(opsStack.peek().toString());
                    opsStack.pop();
                }
                opsStack.push(t.charAt(0));
            }
        }
        while(!opsStack.isEmpty()){
            postFixQ.offer(opsStack.peek().toString());
            opsStack.pop();
        }
    }

    public void calculate(Queue <String> postFixQ, Stack<Double> evalStack){
        String c;
        Double topNum, nextNum;
        double answer =0.0;

        while(!postFixQ.isEmpty()){
            c = postFixQ.peek();
            postFixQ.remove();
            boolean numeric = true;
            try {                                       //https://www.programiz.com/java-programming/examples/check-string-numeric
                double num = Double.parseDouble(c);
            } catch (NumberFormatException e) {
                numeric = false;
            }
            if(numeric){
                evalStack.push(Double.parseDouble(c));
            }
            else{
                
                topNum = evalStack.peek();
                evalStack.pop();
                nextNum = evalStack.peek();
                evalStack.pop();
                switch(c.charAt(0)){
                    case '+': answer = nextNum + topNum; break;
                    case '-': answer = nextNum - topNum; break;
                    case '*': answer = nextNum * topNum; break;
                    case '/': answer = nextNum / topNum; break;
                    case '^': answer = Math.pow(nextNum, topNum); break;
                }
                evalStack.push(answer);

            }

        }
    }

}