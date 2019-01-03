package com.abctreinamentos;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class CursoApp {

	public static void main(String[] args) {
		try {

			Scanner sc = new Scanner(System.in);
			int option = 0;
			int cdcurso;
			String name, url;
			double valor;
			
			Curso curso = null;
			
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAApp");
			EntityManager em = emf.createEntityManager();
			EntityTransaction et = em.getTransaction();

			while (option != 6) {
				System.out.println("======================================");
				System.out.println("= Sistema de Gerenciamento de Cursos =");
				System.out.println("======================================\n");
				System.out.println("Digite [1] para consultar todos os cursos");
				System.out.println("Digite [2] para consultar um curso específico");
				System.out.println("Digite [3] para cadastrar um novo curso");
				System.out.println("Digite [4] para alterar um curso");
				System.out.println("Digite [5] para excluir um curso");
				System.out.println("Digite [6] para sair");
				System.out.println("========================================\n");
				System.out.print("opcao: ");
				option = sc.nextInt();

				switch (option) {
				case 1:
					System.out.println("[1] - Consultar todos");
					String sql = "select c from Curso c";
					TypedQuery<Curso> query = em.createQuery(sql, Curso.class);
					List<Curso> cursos = query.getResultList();
					cursos.forEach(System.out::println);
					System.out.println("Total de cursos cadastrados: " + cursos.size());
					break;
				case 2:
					System.out.println("[2] - Consultar um curso específico");
					System.out.print("Informe o código do curso: ");
					cdcurso = sc.nextInt();
					if(curso != null) curso = null;
					curso = em.find(Curso.class, cdcurso);
					System.out.println(curso);
					break;
				case 3:
					System.out.println("[3] - Cadastrar novo curso");
					System.out.print("Informe o código do curso: ");
					cdcurso = sc.nextInt();
					System.out.print("Informe o nome: ");
					sc.nextLine();
					name = sc.nextLine();
					System.out.print("Informe o valor do curso: ");
					valor = sc.nextDouble();
					System.out.print("Informe a url do curso: ");
					sc.nextLine();
					url = sc.nextLine();
					et.begin();
					em.persist(new Curso(cdcurso, name, valor, url));
					et.commit();
					break;
				case 4:
					System.out.println("[4] - Alterar um curso");
					System.out.print("Informe o código do curso: ");
					cdcurso = sc.nextInt();
					System.out.print("Informe o nome: ");
					sc.nextLine();
					name = sc.nextLine();
					System.out.print("Informe o valor do curso: ");
					valor = sc.nextDouble();
					System.out.print("Informe a url do curso: ");
					sc.nextLine();
					url = sc.nextLine();
					et.begin();
					em.merge(new Curso(cdcurso, name, valor, url));
					et.commit();
					break;
				case 5:
					System.out.println("[5] - Excluir um curso");
					System.out.print("Informe o código do curso: ");
					cdcurso = sc.nextInt();
					curso = em.find(Curso.class, cdcurso);
					et.begin();
					em.remove(curso);
					et.commit();
					break;
				case 6:
					System.out.println("Encerrando o sistema");
					break;
				default:
					System.out.println("Opção inválida");
					break;
				}
			}

			sc.close();
			em.close();
			emf.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
