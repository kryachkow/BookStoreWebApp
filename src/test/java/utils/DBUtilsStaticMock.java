package utils;

import com.task.bookstorewebbapp.db.DBUtils;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public final class DBUtilsStaticMock {

  private static MockedStatic<DBUtils> dbUtilsMockedStatic;
  private static boolean hasMock = false;

  @Mock
  private static DBUtils dbutils;

  private DBUtilsStaticMock(){}

  public static void makeDBMock(){
    if (!hasMock){
      dbUtilsMockedStatic = Mockito.mockStatic(DBUtils.class);
      dbUtilsMockedStatic.when(DBUtils::getInstance).thenReturn(dbutils);
      hasMock = true;
    }
  }
}
