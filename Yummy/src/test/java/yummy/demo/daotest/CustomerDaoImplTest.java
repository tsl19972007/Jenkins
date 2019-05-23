package yummy.demo.daotest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import yummy.demo.dao.CustomerDaoImpl;
import yummy.demo.model.Customer;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomerDaoImplTest {
    @Autowired
    private CustomerDaoImpl cstDao;
    private int cstId;

    @Before
    public void setUp() throws Exception {
        Customer cst=new Customer("email","password","name","phone");
        cstDao.add(cst);
        cstId=cst.getId();
    }

    @After
    public void tearDown() throws Exception {
        cstDao.delete(cstId);
    }

    @Test
    public void findById() {
        assertNotEquals(cstDao.findById(cstId),null);
        assertEquals(cstDao.findById(cstId).getName(),"name");
    }

    @Test
    public void update() {
        Customer cst=new Customer("email","password","name","phone");
        cst.setId(cstId);
        cst.setPassword("password2");
        cstDao.update(cst);
        assertEquals(cstDao.findById(cstId).getPassword(),"password2");
        cst.setPassword("password");
        cstDao.update(cst);
        assertEquals(cstDao.findById(cstId).getPassword(),"password");
    }

    @Test
    public void login() {
        assertEquals(cstDao.findById(cstId).getIsActive(),false);
        assertNotNull(cstDao.login("email","password"));
        cstDao.setActive(cstId);
        assertEquals(cstDao.findById(cstId).getIsActive(),true);
        assertNotNull(cstDao.login("email","password"));
    }

    @Test
    public void findByEmail() {
        assertNotNull(cstDao.findByEmail("email"));
        assertEquals(cstDao.findByEmail("email").getName(),"name");
    }

}