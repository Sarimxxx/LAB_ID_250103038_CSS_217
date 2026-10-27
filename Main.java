
class RealImage {
    private String fileName;

    public RealImage(String fileName) {
        this.fileName = fileName;
        System.out.println("--> [SLOW] Reading " + fileName + " from hard drive...");
    }

    public void show() {
        System.out.println("Displaying " + fileName + " on screen!");
    }
}

class RealVideo {
    private String videoName;

    public RealVideo(String videoName) {
        this.videoName = videoName;
        System.out.println("--> [DOWNLOADING] Connecting to server & buffering 4K video: " + videoName);
    }

    public void play() {
        System.out.println("Playing video: " + videoName);
    }
}

interface Device {
    void turnOn();
    void turnOff();
    void setVolume(int level);
}

class Radio implements Device {
    @Override
    public void turnOn() {
        System.out.println("Radio starts playing static/music.");
    }

    @Override
    public void turnOff() {
        System.out.println("Radio turns off.");
    }

    @Override
    public void setVolume(int level) {
        System.out.println("Radio volume knob turned to: " + level);
    }
}

abstract class BasicRemote {
    protected Device device;

    public BasicRemote(Device device) {
        this.device = device;
    }

    public void powerOn() {
        device.turnOn();
    }
}

class AdvancedRemote extends BasicRemote {
    public AdvancedRemote(Device device) {
        super(device);
    }

    public void mute() {
        System.out.println("[Remote] Mute button pressed!");
        device.setVolume(0);
    }
}


class ProxyImage {
    private String fileName;
    private String userRole;
    private RealImage realImage;

    public ProxyImage(String fileName, String userRole) {
        this.fileName = fileName;
        this.userRole = userRole;
    }

    public void show() {
        if (!"ADMIN".equals(userRole)) {
            System.out.println("[ACCESS DENIED] You lack permission to view: " + fileName);
            return;
        }
        if (realImage == null) {
            realImage = new RealImage(fileName);
        }
        realImage.show();
    }
}


class ProxyVideo {
    private String videoName;
    private RealVideo realVideo;
    private int viewCount = 0;

    public ProxyVideo(String videoName) {
        this.videoName = videoName;
    }

    public void play() {
        viewCount++;
        System.out.println("--> [AUDIT] Video has been played " + viewCount + " time(s).");
        if (realVideo == null) {
            realVideo = new RealVideo(videoName);
        }
        realVideo.play();
    }
}

interface Internet {
    void connectTo(String site);
}

class RealInternet implements Internet {
    @Override
    public void connectTo(String site) {
        System.out.println("Connected to site: " + site);
    }
}

class SchoolProxyInternet implements Internet {
    private RealInternet realInternet;

    @Override
    public void connectTo(String site) {
        if (site.equals("gaming.com") || site.equals("socialmedia.com")) {
            System.out.println("[BLOCKED BY FIREWALL] Cannot access: " + site);
            return;
        }
        if (realInternet == null) {
            realInternet = new RealInternet();
        }
        realInternet.connectTo(site);
    }
}

class SmartLight implements Device {
    @Override
    public void turnOn() {
        System.out.println("Smart Light glows bright white.");
    }

    @Override
    public void turnOff() {
        System.out.println("Smart Light shuts off.");
    }

    @Override
    public void setVolume(int level) {
        System.out.println("Smart Light brightness set to: " + level + "%");
    }
}

class PartyRemote extends BasicRemote {
    public PartyRemote(Device device) {
        super(device);
    }

    public void partyMode() {
        System.out.println("[Party Remote] Pumping up the party!");
        device.turnOn();
        device.setVolume(100);
    }
}

interface Weapon {
    void use();
}

class Sword implements Weapon {
    @Override
    public void use() {
        System.out.println("Slash! Dealing 20 physical damage.");
    }
}

class MagicWand implements Weapon {
    @Override
    public void use() {
        System.out.println("Zap! Dealing 35 magic damage.");
    }
}

abstract class Hero {
    protected Weapon weapon;

    public Hero(Weapon weapon) {
        this.weapon = weapon;
    }

    public abstract void attack();
}

class Knight extends Hero {
    public Knight(Weapon weapon) {
        super(weapon);
    }

    @Override
    public void attack() {
        weapon.use();
    }
}

class Wizard extends Hero {
    public Wizard(Weapon weapon) {
        super(weapon);
    }

    @Override
    public void attack() {
        weapon.use();
    }
}


public class Main {
    public static void main(String[] args) {
        ProxyImage guestImg = new ProxyImage("salary_sheet.png", "GUEST");
        guestImg.show();
        ProxyImage adminImg = new ProxyImage("salary_sheet.png", "ADMIN");
        adminImg.show();

        ProxyVideo video = new ProxyVideo("Cat_Memes.mp4");
        video.play();
        video.play();
        video.play();

        Internet schoolNet = new SchoolProxyInternet();
        schoolNet.connectTo("wikipedia.org");
        schoolNet.connectTo("gaming.com");

        Device light = new SmartLight();
        AdvancedRemote advRemote = new AdvancedRemote(light);
        advRemote.powerOn();
        advRemote.mute();

        Device radio = new Radio();
        PartyRemote partyRemote = new PartyRemote(radio);
        partyRemote.partyMode();

        Hero knightWithSword = new Knight(new Sword());
        knightWithSword.attack();

        Hero knightWithWand = new Knight(new MagicWand());
        knightWithWand.attack();
    }
}