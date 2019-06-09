package lab_13;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationListener;

public class Slogan extends NotificationBroadcasterSupport implements SloganMBean {

	private String slogan1;
	private int duration1;
	private String slogan2;
	private int duration2;
	private String slogan3;
	private int duration3;
	private Boolean running = false;
	private long sequenceNumber = 1;



	public Boolean getRunning() {
		return running;
	}

	public void setRunning(Boolean running) {
		this.running = running;
	}

	@Override
	public String getSlogan1() {
		System.out.println("slogan1 " + slogan1);
		return slogan1;
	}

	@Override
	public void setSlogan1(String slogan1) {
		String oldSlogan1 = this.slogan1;
		System.out.println("slogan1 " + slogan1);
		this.slogan1 = slogan1;
		Notification n = new AttributeChangeNotification(this, sequenceNumber++, System.currentTimeMillis(),
				"slogan1 changed", "slogan1", "String", oldSlogan1, this.slogan1);

		sendNotification(n);
	}

	@Override
	public int getDuration1() {
		System.out.println("duration1 " + duration1);
		return duration1;
	}

	@Override
	public void setDuration1(int duration1) {
		int oldDuration1 = this.duration1;
		System.out.println("duration1 " + duration1);
		this.duration1 = duration1;
		Notification n = new AttributeChangeNotification(this, sequenceNumber++, System.currentTimeMillis(),
				"duration1 changed", "duration1", "int", oldDuration1, this.duration1);

		sendNotification(n);
	}

	@Override
	public String getSlogan2() {
		System.out.println("slogan2 " + slogan2);
		return slogan2;
	}

	@Override
	public void setSlogan2(String slogan2) {
		String oldSlogan2 = this.slogan2;
		System.out.println("slogan2 " + slogan2);
		this.slogan2 = slogan2;
		Notification n = new AttributeChangeNotification(this, sequenceNumber++, System.currentTimeMillis(),
				"slogan2 changed", "slogan2", "String", oldSlogan2, this.slogan2);

		sendNotification(n);
	}

	@Override
	public int getDuration2() {
		System.out.println("duration2 " + duration2);
		return duration2;
	}

	@Override
	public void setDuration2(int duration2) {
		int oldDuration2 = this.duration2;
		System.out.println("duration2 " + duration2);
		this.duration2 = duration2;
		Notification n = new AttributeChangeNotification(this, sequenceNumber++, System.currentTimeMillis(),
				"duration2 changed", "duration2", "int", oldDuration2, this.duration2);

		sendNotification(n);
	}

	@Override
	public String getSlogan3() {
		System.out.println("slogan3 " + slogan3);
		return slogan3;
	}

	@Override
	public void setSlogan3(String slogan3) {
		String oldSlogan3 = this.slogan3;
		System.out.println("slogan3 " + slogan3);
		this.slogan3 = slogan3;
		Notification n = new AttributeChangeNotification(this, sequenceNumber++, System.currentTimeMillis(),
				"slogan3 changed", "slogan3", "String", oldSlogan3, this.slogan3);

		sendNotification(n);
	}

	@Override
	public int getDuration3() {
		System.out.println("duration3 " + duration3);
		return duration3;
	}

	@Override
	public void setDuration3(int duration3) {
		int oldDuration3 = this.duration3;
		System.out.println("duration3 " + duration3);
		this.duration3 = duration3;
		Notification n = new AttributeChangeNotification(this, sequenceNumber++, System.currentTimeMillis(),
				"duration3 changed", "duration3", "int", oldDuration3, this.duration3);

		sendNotification(n);
	}

	@Override
	public void startStop() {
		// TODO Auto-generated method stub
		if (this.getRunning() == true) {
			System.out.println("tu1");
			this.setRunning(false);
		} else if (this.getRunning() == false) {
			System.out.println("tu2");
			this.setRunning(true);
		}
	}

	@Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[]{
            AttributeChangeNotification.ATTRIBUTE_CHANGE
        };

        String name = AttributeChangeNotification.class.getName();
        String description = "An attribute of this MBean has changed";
        MBeanNotificationInfo info =
                new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[]{info};
    }
}
