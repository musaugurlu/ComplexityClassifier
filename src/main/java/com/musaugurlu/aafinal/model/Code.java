package com.musaugurlu.aafinal.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Code {
    private long id;
    private String code;
    private int totalLines;
    private int loops;
    private int loopDepth;
    private int recursions;
    private String methodName;
    private String complexity;

    private Pattern p;
    private Matcher m;

    public Code() { this.totalLines = 0;}

    public Code(String code) {
        this.code = code;
        this.totalLines = 0;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTotalLines() {
        this.totalLines = 0;
        if (isValid()) {
            String[] codeArray = code.trim().split("\n");
            this.totalLines = codeArray.length;
        }
        return this.totalLines;
    }

    public int getLoops() {
        this.loops = 0;
        int level = 0;
        int brackets = 0;
        boolean inLoop = false;
        if(getTotalLines() > 0) {
            String[] codeArray = code.trim().split("\n");
            for(String line: codeArray) {
                p = Pattern.compile("\\b(for[\\s]?\\()|(while[\\s]?\\()");
                m = p.matcher(line.trim());

                if (m.find()) {
                    this.loops++;
                    level = brackets + 1;
                    inLoop = true;
                }
                if (inLoop) {
                    brackets += countChars(line, '{') - countChars(line, '}');
                }

                if (brackets == 0) {
                    inLoop = false;
                    this.loopDepth = Math.max(this.loopDepth, level);
                }
            }
        }

        return this.loops;
    }

    public int getLoopDepth() {
        return loopDepth;
    }

    public int getRecursions() {
        this.recursions = 0;
        this.methodName = getMethodName();
        if (methodName != null) {
            String[] codeArray = code.trim().split("\n");
            for (int i = 1; i < codeArray.length;i++) {
                p = Pattern.compile("\\b(" + methodName + "[\\s]?\\()");
                m = p.matcher(codeArray[i]);

                while (m.find()) {
                    this.recursions++;
                }
            }
        }
        return this.recursions;
    }

    public String getMethodName() {
        if (isValid()) {
            String firstLine = code.trim().split("\n")[0];
            p = Pattern.compile("([a-zA-Z_{1}][a-zA-Z0-9_]+)(?=\\()");
            m = p.matcher(firstLine);

            if(m.find()){
                this.methodName = m.group(1);
            }

            return this.methodName;
        }
        return null;
    }

    public String getComplexity() {
        if (!isValid()) {
            return "Invalid Code";
        }

        if (getLoops() == 0 && getRecursions() == 0) {
            return ComplexityType.Constant.name();
        } else if (getLoopDepth() == 1 && getRecursions() == 0) {
            return ComplexityType.Linear.name();
        }

        return "Other";
    }

    public boolean isValid() {
        return (
                 code != null &&
                 !code.trim().equals(" ") &&
                 !code.trim().isEmpty() &&
                 !code.trim().isBlank()
        );
    }

    private int countChars(String text, char c) {
        int count = 0;
        for (char i : text.toCharArray()){
            if (i == c)
                count++;
        }
        return count;
    }

}
