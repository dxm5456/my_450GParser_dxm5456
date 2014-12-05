package edu.louisiana.cacs.csce450Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import edu.louisiana.cacs.csce450GProject.Output;

public class Parser {
	/*
	 * YOUR CODE GOES HERE
	 * 
	 * You must implement two methods 1. parse 2. printParseTree
	 * 
	 * Print the intermediate states of the parsing process, including the intermediate states of the parse tree,make as specified in the class handout. If the input is legal according to the grammar, print ACCEPT, else UNGRAMMATICAL. If the parse is successful, print the final parse tree.
	 * 
	 * You can modify the input and output of these function but not the name
	 */
	String fileName;
	public Map<String, String> actionLookUpMap;
	public Map<String, String> gotoLookUpMap;
	public Map<Integer, String> grammerMap;
	Stack<String> outputStack;

	List<Output> outPutList;
	String stackLookUpAction = "0";
	String remainingToken = "";
	String tempStackValue = "0";
	String gotoLHSVal = "0";
	Integer step = 1;

	public Parser(String fileName) {
		System.out.println("File to parse : " + fileName);
		this.fileName = fileName;
		actionLookUpMap = new LinkedHashMap<String, String>();
		gotoLookUpMap = new LinkedHashMap<String, String>();
		grammerMap = new LinkedHashMap<Integer, String>();
		actionLookUpMap.put("0#", "S5");
		actionLookUpMap.put("0(", "S4");
		actionLookUpMap.put("1+", "S6");
		actionLookUpMap.put("1$", "accept");

		actionLookUpMap.put("2+", "R2");
		actionLookUpMap.put("2*", "S7");
		actionLookUpMap.put("2)", "R2");
		actionLookUpMap.put("2$", "R2");

		actionLookUpMap.put("3+", "R4");
		actionLookUpMap.put("3*", "R4");
		actionLookUpMap.put("3)", "R4");
		actionLookUpMap.put("3$", "R4");

		actionLookUpMap.put("4#", "S5");
		actionLookUpMap.put("4(", "S4");

		actionLookUpMap.put("5+", "R6");
		actionLookUpMap.put("5*", "R6");
		actionLookUpMap.put("5)", "R6");
		actionLookUpMap.put("5$", "R6");

		actionLookUpMap.put("6#", "S5");
		actionLookUpMap.put("6(", "S4");

		actionLookUpMap.put("7#", "S5");
		actionLookUpMap.put("7(", "S4");

		actionLookUpMap.put("8+", "S6");
		actionLookUpMap.put("8)", "S11");

		actionLookUpMap.put("9+", "R1");
		actionLookUpMap.put("9*", "S7");
		actionLookUpMap.put("9)", "R1");
		actionLookUpMap.put("9$", "R1");

		actionLookUpMap.put("10+", "R3");
		actionLookUpMap.put("10*", "R3");
		actionLookUpMap.put("10)", "R3");
		actionLookUpMap.put("10$", "R3");

		actionLookUpMap.put("11+", "R5");
		actionLookUpMap.put("11*", "R5");
		actionLookUpMap.put("11)", "R5");
		actionLookUpMap.put("11$", "R5");

		gotoLookUpMap.put("0E", "1");
		gotoLookUpMap.put("0T", "2");
		gotoLookUpMap.put("0F", "3");

		gotoLookUpMap.put("4E", "8");
		gotoLookUpMap.put("4T", "2");
		gotoLookUpMap.put("4F", "3");

		gotoLookUpMap.put("6T", "9");
		gotoLookUpMap.put("6F", "3");

		gotoLookUpMap.put("7F", "10");

		grammerMap.put(1, "E:E+T");
		grammerMap.put(2, "E:T");
		grammerMap.put(3, "T:T*F");
		grammerMap.put(4, "T:F");
		grammerMap.put(5, "F:(E)");
		grammerMap.put(6, "F:#");
		outPutList = new ArrayList<Output>();
		outputStack = new Stack<String>();
		outputStack.push("0");
	}

	/*
	 * Dummy code
	 */
	public void printParseTree() {
		System.out.println("Hello World from " + getClass().getName());
	}

	/*
	 * Dummy code
	 */
	public void parse() {
		System.out.println("START: Processing of step " + step++);
		try{
		File file = new File(fileName);
		String fileStr = file.getAbsolutePath();
		BufferedReader br = new BufferedReader(new FileReader(fileStr));
		String sCurrentLine;
		System.out.println("ACCEPT");
		System.out.println(" ");
		while ((sCurrentLine = br.readLine()) != null) {
			String fullToken = replaceIdWithHash(sCurrentLine);
			// Parser lrParser = new Parser();
			List<Output> outPutList = this.doParse(fullToken);
			System.out.println("Stack 		Input Tokens	LookUp		A/Value		Val/LHS		Length/RHS		tempStack		gotoLookup			gotoVal			S/Action		P/T/Stack");
			System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println(" ");
			for (Output o : outPutList) {
				System.out.print((o.getStack() != null ? o.getStack() : "") + " 		" + (o.getInputTokens() != null ? o.getInputTokens() : "") + " 		" + (o.getActionLookup() != null ? o.getActionLookup() : "") + "  		" + (o.getActionValue() != null ? o.getActionValue() : "") + "  		"
						+ (o.getValueOfLHS() != null ? o.getValueOfLHS() : "") + "  		" + (o.getLengthOfRHS() != null ? o.getLengthOfRHS() : "") + "  		" + (o.getTempStack() != null ? o.getTempStack() : "") + "  		" + (o.getGotoLookup() != null ? o.getGotoLookup() : "") + " 		"
						+ (o.getGotoValue() != null ? o.getGotoValue() : "") + "  		" + (o.getStackAction() != null ? o.getStackAction() : "") + "   	" + (o.getParseTreeStack() != null ? o.getParseTreeStack() : ""));
				System.out.println(" ");
				// System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
			}
		}
		}catch(FileNotFoundException e){
			e.printStackTrace();
			System.out.print("File Not found");
		}catch(IOException e){
			e.printStackTrace();
			System.out.print("File Not found");
		}
		// printParseTree();
	}

	public List<Output> doParse(String inputToken) {
		Output output = new Output();
		String token = "";
		output.setStack(replaceHashWithId(outputStack.peek()));
		output.setInputTokens(replaceHashWithId(inputToken));
		try {
			if (inputToken != null && inputToken.trim().length() > 0) {
				token = inputToken.substring(0, 1);
				remainingToken = inputToken.substring(1);
				String actionLkpValue = actionLookUpMap.get(stackLookUpAction + token);
				if (null == actionLkpValue) {
					throw new Exception("UNGRAMMATICAL");
				}
				output.setActionLookup("[" + stackLookUpAction + "," + replaceHashWithId(token) + "]");
				output.setActionValue(actionLkpValue);
				if (actionLkpValue.startsWith("S")) {
					output.setStackAction("push " + replaceHashWithId(token) + actionLkpValue.substring(1));
					pushIntoStack(outputStack, token, actionLkpValue);
					outPutList.add(output);
					doParse(remainingToken);

				} else if (actionLkpValue.startsWith("R")) {
					Integer grammerId = Integer.parseInt(actionLkpValue.substring(1));
					String grammerLkUpVal = grammerMap.get(grammerId);
					String[] grammer = grammerLkUpVal.split(":");
					String valLHS = grammer[0];
					output.setValueOfLHS(valLHS);
					Integer lengthOfRHS = grammer[1].length();
					output.setLengthOfRHS(lengthOfRHS.toString());
					for (int i = 0; i < lengthOfRHS; i++) {
						outputStack.pop();
					}
					tempStackValue = outputStack.peek();
					gotoLHSVal = tempStackValue.substring(tempStackValue.length() - 1);
					output.setTempStack(tempStackValue);
					output.setGotoLookup("[" + gotoLHSVal + "," + valLHS + "]");
					String gotoLkpVal = gotoLookUpMap.get(gotoLHSVal + valLHS);
					if(null==gotoLkpVal){
						throw new Exception("UNGRAMMATICAL");
					}
					output.setStackAction("push " + valLHS + gotoLkpVal);
					StringBuffer finalVal = new StringBuffer();
					finalVal.append(tempStackValue);
					/*
					 * for (String s : outputStack) { finalVal.append(s); }
					 */
					finalVal.append(valLHS).append(gotoLkpVal);
					// pushIntoStack(outputStack, token, gotoLookUpMap.get(tempStackValue+valLHS));
					// outputStack.push(valLHS+gotoLookUpMap.get(tempStackValue+valLHS));
					outputStack.push(finalVal.toString());
					stackLookUpAction = gotoLkpVal;
					outPutList.add(output);
					printIntermediateStep(output);
					doParse(inputToken);

				} else if (actionLkpValue.equals("accept")) {
					outPutList.add(output);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("UNGRAMMATICAL");
		}
		return outPutList;

	}

	private void printIntermediateStep(Output o) {
		System.out.print((o.getStack() != null ? o.getStack() : "") + " 		" + (o.getInputTokens() != null ? o.getInputTokens() : "") + " 		" + (o.getActionLookup() != null ? o.getActionLookup() : "") + "  		" + (o.getActionValue() != null ? o.getActionValue() : "") + "  		"
				+ (o.getValueOfLHS() != null ? o.getValueOfLHS() : "") + "  		" + (o.getLengthOfRHS() != null ? o.getLengthOfRHS() : "") + "  		" + (o.getTempStack() != null ? o.getTempStack() : "") + "  		" + (o.getGotoLookup() != null ? o.getGotoLookup() : "") + " 		"
				+ (o.getGotoValue() != null ? o.getGotoValue() : "") + "  		" + (o.getStackAction() != null ? o.getStackAction() : "") + "   	" + (o.getParseTreeStack() != null ? o.getParseTreeStack() : ""));
		System.out.println(" ");
	}

	private void pushIntoStack(Stack<String> outputStack, String token, String actionLkpValue) {
		StringBuffer finalVal = new StringBuffer();
		finalVal.append(outputStack.get(outputStack.size() - 1));
		finalVal.append(token);
		finalVal.append(actionLkpValue.substring(1));
		stackLookUpAction = actionLkpValue.substring(1);
		outputStack.push(finalVal.toString());
	}

	private String replaceHashWithId(String fullToken) {
		String token = fullToken.replace("#", "id");
		return token;
	}

	private static String replaceIdWithHash(String fullToken) {
		String token = fullToken.replace("id", "#");
		return token;
	}

}