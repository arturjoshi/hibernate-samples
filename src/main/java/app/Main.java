package app;

import app.hibernatereference.chapter2.lobs.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.BlobProxy;
import org.hibernate.engine.jdbc.ClobProxy;

import java.io.Serializable;

/**
 * Created by ajoshi on 12-Sep-16.
 */
public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateConfigurator.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        testClobs(session);
        session.flush();
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

//    private static void testSimpleEntitySaving(Session session) {
//        Employee employee = new Employee();
//        employee.setName("kk");
//        employee.setSex(true);
//        employee.setPhoneType(PhoneType.MOBILE);
//        session.save(employee);
//    }
//
//    private static void testCustomBasicType(Session session) {
//        BitSet bitSet = BitSet.valueOf(new long[]{10, 20, 30});
//        Product product = new Product();
//        product.setId(1);
//        product.setBitSet(bitSet);
//        session.save(product);
//    }
//
//    private static void testEnums(Session session) {
//        Phone phone = new Phone();
//        phone.setId(1L);
//        phone.setNumber("0961212314");
//        phone.setPhoneType(PhoneType.MOBILE);
//        session.save(phone);
//        System.out.println("saved");
//    }
//
//    private static void testEnumsAttributeConverter(Session session) {
//        Person person = new Person();
//        person.setName("aj");
//        person.setGender(Gender.MALE);
//        session.save(person);
//    }

    private static void testClobs(Session session) {
        String warranty = "My product warranty";
        Product product = new Product();
        product.setId(1);
        product.setName("Phone");
        session.doWork(connection -> product.setWarranty(ClobProxy.generateProxy(warranty)));
        session.doWork(connection -> product.setImage(BlobProxy.generateProxy(new byte[]{1, 2, 3})));
        Serializable saved = session.save(product);
        System.out.println(saved);

//        Reading clobs
//        Product product = entityManager.find( Product.class, productId );
//        try (Reader reader = product.getWarranty().getCharacterStream()) {
//            assertEquals( "My product warranty", toString( reader ) );
//        }

//        Reading blobs
//        Product product = entityManager.find( Product.class, productId );
//
//        try (InputStream inputStream = product.getImage().getBinaryStream()) {
//            assertArrayEquals(new byte[] {1, 2, 3}, toBytes( inputStream ) );
//        }
    }

}
