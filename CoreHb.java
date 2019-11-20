/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corehb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.persistence.TypedQuery;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 *
 * @author avis
 */
public class CoreHb {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        first();
//        second();
    }

    public static void first() {

        Session session = null;
        Transaction t = null;
        try {
            StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("corehb/hibernate.cfg.xml").build();
            Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
            SessionFactory factory = meta.getSessionFactoryBuilder().build();
            session = factory.openSession();
            Organisation org = session.get(Organisation.class, 1);
            System.out.println("id=========" + org.getId());
            System.out.println("NAME======" + org.getName());
            List<Employee> emp = org.getEmployees();
            emp.forEach(n -> {
                System.out.println("empid=======" + n.getId());
                System.out.println("FirstName=======" + n.getFirstName());
                System.out.println("LastName=======" + n.getLastName());

            });
            System.out.println("success , data ftched-----------");

        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    public static void second() {

        Session session = null;
        Transaction t = null;
        try {
            StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("corehb/hibernate.cfg.xml").build();
            Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
            SessionFactory factory = meta.getSessionFactoryBuilder().build();
            session = factory.openSession();
            t = session.beginTransaction();
            List<Employee> list = new ArrayList<>();
            Employee employee = new Employee();
            employee.setFirstName("narendra");
            employee.setLastName("Singh");
            employee.setSalary(4000);
            Employee employee1 = new Employee();
            employee1.setFirstName("lovely");
            employee1.setLastName("Kumari");
            employee1.setSalary(40000);
            list.add(employee);
            list.add(employee1);
            Organisation organisation = new Organisation();
            organisation.setName("AVis");
            organisation.setAddress("noida 2");
            organisation.setEmployees(list);
            session.persist(organisation);
            t.commit();
            System.out.println("success , data stored-----------");

        } catch (Exception e) {
            e.printStackTrace(System.out);
            if (t != null && t.isActive()) {
                t.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

}
