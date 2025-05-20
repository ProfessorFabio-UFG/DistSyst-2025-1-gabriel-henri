/*
 * Copyright (c) 2004, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * -Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in
 *  the documentation and/or other materials provided with the
 *  distribution.
 *
 * Neither the name of Oracle nor the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR
 * ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 */

 package example.hello;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class Client {

    private Client() {}

    public static void main(String[] args) {
        System.out.println("Iniciando cliente...");

        String host = (args.length < 1) ? null : args[0]; // Se nenhum IP for passado, assume localhost
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            System.out.println("Registro localizado.");

            Hello stub = (Hello) registry.lookup("Hello");
            System.out.println("Servidor encontrado.\n");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("==== MENU ====");
                System.out.println("1 - Adicionar elemento");
                System.out.println("2 - Remover elemento");
                System.out.println("3 - Verificar se elemento está na lista");
                System.out.println("4 - Ver tamanho da lista");
                System.out.println("5 - Listar todos os elementos");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");

                int option = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer

                switch (option) {
                    case 1:
                        System.out.print("Digite o elemento para adicionar: ");
                        String toAdd = scanner.nextLine();
                        System.out.println(stub.addElement(toAdd));
                        break;
                    case 2:
                        System.out.print("Digite o elemento para remover: ");
                        String toRemove = scanner.nextLine();
                        System.out.println(stub.removeElement(toRemove));
                        break;
                    case 3:
                        System.out.print("Digite o elemento para verificar: ");
                        String toCheck = scanner.nextLine();
                        boolean exists = stub.containsElement(toCheck);
                        System.out.println("Está na lista? " + (exists ? "Sim" : "Não"));
                        break;
                    case 4:
                        System.out.println("Tamanho da lista: " + stub.getSize());
                        break;
                    case 5:
                        List<String> elements = stub.getAllElements();
                        System.out.println("Elementos na lista:");
                        for (String el : elements) {
                            System.out.println("- " + el);
                        }
                        break;
                    case 0:
                        System.out.println("Encerrando cliente.");
                        return;
                    default:
                        System.out.println("Opção inválida.");
                }

                System.out.println(); // Linha em branco para separar ciclos
            }

        } catch (Exception e) {
            System.err.println("Exceção no cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}
