import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.plaf.nimbus.State;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbManagerTestSuite {
    @Test
    public void testConnect() throws SQLException {
        //given
        //when
        DbManager dbManager = DbManager.getInstance();
        //then
        Assert.assertNotNull(dbManager.getConnection());
    }



    @Test
    public void testSelectUsers() throws SQLException{
        //given
        DbManager dbManager = DbManager.getInstance();
        //when
        String sqlQuery = "SELECT * FROM USERS";
        Statement statement = dbManager.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sqlQuery);
        //then
        int counter = 0;
        while(rs.next()){
            System.out.println(rs.getInt("ID") + "," +
                    rs.getString("FIRSTNAME") + ","+
                    rs.getString("LASTNAME"));
                    counter++;
        }
        rs.close();
        statement.close();
        Assert.assertEquals(5,counter);
    }

    @Test
    public void testSelectUsersAndPosts() throws SQLException{
        //given
        DbManager dbManager = DbManager.getInstance();
        String sqlQuery = "SELECT U.FIRSTNAME AS IMIE, U.LASTNAME AS NAZWISKO,  COUNT(*) AS ILE_ZADAN\n" +
                "FROM USERS U JOIN ISSUES I ON U.ID = I.USER_ID_ASSIGNEDTO\n" +
                "GROUP BY U.ID\n" +
                "HAVING COUNT(*) >=2\n" +
                "ORDER BY U.LASTNAME, U.FIRSTNAME";
        Statement statement = dbManager.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sqlQuery);
        int counter = 0;
        //then
        while(rs.next()){
            System.out.println(
                    rs.getString("IMIE") + " , "+
                    rs.getString("NAZWISKO") + " , "+
                    rs.getInt("ILE_ZADAN"));
            counter++;
        }
        Assert.assertEquals(4,counter);
    }
}
