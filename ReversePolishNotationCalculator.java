import java.io.*;
import java.util.*;
import java.util.Stack;
import java.lang.*;



class ReversePolishNotationCalculator{


    public static void main(String[] args){
        
        Queue<String> infixQ = new LinkedList<String>();
        Stack<Character> opStack = new Stack<Character>();
        Queue<String> postFixQ = new LinkedList<String>();
        Stack<Integer> evalStack = new Stack<Integer>();
        //Stack<Character> stack = new Stack<>();
        ReversePolishNotationCalculator r = new ReversePolishNotationCalculator();
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter the expression: ");
        String infixExp = sc.nextLine();

        // for (char c : infixExp.toCharArray()) {
        //     //char temp = '\0';
        //     String tmp = Character.toString(c);
        //     String temp ="";
            
        //     if(tmp !="+" || tmp!= "-" || tmp!="*" || tmp!="/" || tmp!="(" || tmp != ")" || tmp!="^"){
        //         temp += tmp;
        //     }
        //     else{
        //         infixQ.offer(temp);
        //         infixQ.offer(tmp);
        //         temp = "";
        //     }
        // }
        //infixQ.add("e");
        String temp = "";
        for(int i = 0; i<infixExp.length();i++){
            char c = infixExp.charAt(i);
            if((i==0 && c == '-') ||(i > 0 && c == '-' && c == '(')){
                System.exit(0);
            }
            else if(Character.isDigit(c)){//infixExp.charAt(i) != '+' || infixExp.charAt(i) != '-' || infixExp.charAt(i) != '*' || infixExp.charAt(i) != '/' || infixExp.charAt(i) != '(' || infixExp.charAt(i) != ')'){
                //char cc = infixExp.charAt(i);

                temp +=c;
                if(i==infixExp.length()-1){
                    infixQ.offer(temp);
                    temp = "";
                }

            }
            else{
                //String tmp = Character.toString(temp);
                if(!temp.equals("")){
                    infixQ.offer(temp);
                }
                //System.out.print("hi");
                String s = Character.toString(c);
                infixQ.offer(s);
                temp = "";
            }
        }


        System.out.println(infixQ);
        r.infixToPostfix(infixQ,opStack,postFixQ);
        System.out.println(postFixQ);
        r.calculate(postFixQ, evalStack);
        System.out.println("the result is "+ evalStack.peek());

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
            //int tempNum = Integer.parseInt(t);
            if(numeric){
                postFixQ.offer(t);
                //System.out.print(t);
            }
            else if(opsStack.isEmpty()){
                //char op = t.;
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

    public void calculate(Queue <String> postFixQ, Stack<Integer> evalStack){
        String c;
        int topNum, nextNum;
        int answer =0;

        while(!postFixQ.isEmpty()){
            c = postFixQ.peek();
            postFixQ.remove();
            boolean numeric = true;
            try {                                       //https://www.programiz.com/java-programming/examples/check-string-numeric
                int num = Integer.parseInt(c);
            } catch (NumberFormatException e) {
                numeric = false;
            }
            if(numeric){
                evalStack.push(Integer.parseInt(c));
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
                }
                evalStack.push(answer);

            }



        }
    }

}