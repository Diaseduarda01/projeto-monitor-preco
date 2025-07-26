package com.monitora.preco.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void info(String mensagem) {
        log("INFO", mensagem, null);
    }

    public static void warn(String mensagem) {
        log("WARN", mensagem, null);
    }

    public static void error(String mensagem) {
        log("ERROR", mensagem, null);
    }

    public static void error(String mensagem, Exception e) {
        log("ERROR", mensagem, e);
    }

    private static void log(String nivel, String mensagem, Exception e) {
        String timestamp = LocalDateTime.now().format(formatter);
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String origem = "UNKNOWN";

        if (stackTrace.length >= 4) {
            StackTraceElement caller = stackTrace[3];
            origem = caller.getClassName() + "#" + caller.getMethodName() + ":" + caller.getLineNumber();
        }

        String logFormatado = String.format("[%s] [%s] [%s] %s", timestamp, nivel, origem, mensagem);
        System.out.println(logFormatado);

        if (e != null) {
            e.printStackTrace(System.out);
        }
    }

    public static void logProduto(String acao, String nomeProduto, String infoExtra) {
        info(String.format("Produto: '%s' | Ação: %s | Info: %s", nomeProduto, acao, infoExtra));
    }

    public static void logUsuario(String acao, String nomeUsuario, String infoExtra) {
        info(String.format("Produto: '%s' | Ação: %s | Info: %s", nomeUsuario, acao, infoExtra));
    }

    public static void logMonitor(String acao, String nomeMonitor, String infoExtra) {
        info(String.format("Produto: '%s' | Ação: %s | Info: %s", nomeMonitor, acao, infoExtra));
    }
}
