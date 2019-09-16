package packageForTest.test;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.web.entity.MovieBean;
import com.web.entity.SeatBean;
import com.web.entity.TimeTableBean;

import packageForTest.util.HibernateUtils;

public class TimeTableDataForTest {

	public static void main(String[] args) {
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			TimeTableBean tb = new TimeTableBean();
			tb.setAvailable(true);
			tb.setDuration(120);
			tb.setStartTime(new Date(29, 9, 10, 12, 20));
			tb.setEndTime(new Date(29, 9, 10, 14, 20));
			tb.setTheater("B");
			tb.setVersion("3D");
			tb.setMovie(session.get(MovieBean.class, 1));
			tb.setMovieName(tb.getMovie().getMovieName());
			Set<SeatBean> seats = new HashSet<>();
			tb.setSeats(seats);
			char row = 'A';
			for (int i = 0; i < 15; i++) {
				SeatBean sb = null;
				for (int col = 1; col <= 20; col++) {
					sb = new SeatBean();
					sb.setAvailable(true);
					sb.setRow(String.valueOf((char)(row + i)));
					sb.setColumn(String.valueOf(col));
					seats.add(sb);
				}
			}
			session.save(tb);
			tx.commit();
			System.out.println("========新增timeTable成功============");
		} catch (Exception e) {
			tx.rollback();
			System.out.println("========新增timeTable失敗============");
			e.printStackTrace();
		}
		session.close();
		factory.close();
	}

}
