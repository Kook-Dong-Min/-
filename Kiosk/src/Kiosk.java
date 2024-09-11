import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Kiosk {
    public static void main(String[] args) {
        new Order();
    }
}

class Order extends JFrame
{
    JPanel panel_order1;
    JPanel panel_order2;
    JButton for_here;
    JButton to_go;

    ImageIcon for_here_img=new ImageIcon("./image/for_here.PNG");
    ImageIcon to_go_img=new ImageIcon("./image/to_go.PNG");

    public Order() {
        setTitle("주문하기");
        setBounds(100,100,700,400);

        setLayout(new FlowLayout());

        panel_order1=new JPanel();
        panel_order2=new JPanel();
        for_here=new JButton(for_here_img); // 매장, 포장 버튼
        to_go=new JButton(to_go_img);

        JFrame frame=this;

        panel_order1.add(for_here);
        panel_order2.add(to_go);
        add(panel_order1);
        add(panel_order2);

        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for_here.addActionListener(new ActionListener() { // 매장 버튼 눌렀을 때
            @Override
            public void actionPerformed(ActionEvent e) {
                For_Here_Menu menu=new For_Here_Menu(frame,true);
                menu.setVisible(true);
            }
        });

        to_go.addActionListener(new ActionListener() { // 포장 버튼 눌렀을 때
            @Override
            public void actionPerformed(ActionEvent e) {
                To_Go_Menu menu=new To_Go_Menu(frame, true);
                menu.setVisible(true);
            }
        });
    }
}

class Menu extends JDialog // 공통 메뉴판
{
    static int price=0;

    JTabbedPane panel_menu;
    JPanel panel_menu_best;
    JPanel panel_menu_coffee;
    JPanel panel_menu_non_coffee;
    JPanel panel_menu_smoothie;
    JPanel panel_menu_juice;
    JPanel panel_menu_dessert;
    JTextArea name_cnt;
    JTextField sum;

    public Menu(Frame parent, boolean modal)
    {
        super(parent,modal);
        setTitle("메뉴");
        setBounds(100,100,1500,800);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        Button payment=new Button("결제");
        Button back=new Button("뒤로 가기");

        JPanel panel=new JPanel(); // 메인 패널

        add(panel);

        panel_menu=new JTabbedPane(); // 메뉴 패널
        JPanel panel_top=new JPanel();
        JPanel panel_bottom=new JPanel();

        name_cnt =new JTextArea(); // 아래쪽 텍스트박스
        sum=new JTextField();
        name_cnt.setEditable(false);
        sum.setEditable(false);
        panel_bottom.add(name_cnt);
        panel_bottom.add(sum);

        panel.setLayout(new BorderLayout()); // 패널 배치
        panel.add(panel_top,"North");
        panel.add(panel_menu,"Center");
        panel.add(panel_bottom,"South");

        panel_top.add(payment);
        panel_top.add(back);

        panel_menu_best=new JPanel(); // 탭으로 넣을 패널들
        panel_menu_coffee=new JPanel();
        panel_menu_non_coffee=new JPanel();
        panel_menu_smoothie=new JPanel();
        panel_menu_juice=new JPanel();
        panel_menu_dessert=new JPanel();

        panel_menu.add(panel_menu_best,"베스트");
        panel_menu.add(panel_menu_coffee,"커피");
        panel_menu.add(panel_menu_non_coffee,"논커피");
        panel_menu.add(panel_menu_smoothie,"스무디");
        panel_menu.add(panel_menu_juice,"주스");
        panel_menu.add(panel_menu_dessert,"디저트");

        back.addActionListener(new ActionListener() { // 뒤로 가기 버튼 눌렀을 때
            @Override
            public void actionPerformed(ActionEvent e) {
                setPrice(0);
                setVisible(false);
            }
        });
    }

    public String getName_cnt()
    {
        return name_cnt.getText();
    }

    public void setName_cnt(String string)
    {
        name_cnt.setText(string);
    }

    public int getPrice() // 가격 조정 메소드들
    {
        return price;
    }

    public void setPrice(int price)
    {
        if(price<0) return;
        this.price=price;
    }

    public void setPricePlus(int priceUp)
    {
        this.price+=priceUp;
    }
}

class For_Here_Menu extends Menu // 방문 메뉴
{
    int cold_brew_float_cnt=0;
    int cold_brew_malt_cnt=0;

    ImageIcon cold_brew_float_img=new ImageIcon("./image/cold_brew_float.PNG");
    ImageIcon cold_brew_malt_img=new ImageIcon("./image/cold_brew_malt.PNG");

    public For_Here_Menu(Frame parent, boolean modal)
    {
        super(parent,modal);

        Menu menu=this;

        JButton cold_brew_float=new JButton(cold_brew_float_img); // 베스트 메뉴 커피 버튼
        JButton cold_brew_malt=new JButton(cold_brew_malt_img);

        panel_menu_best.add(cold_brew_float);
        panel_menu_best.add(cold_brew_malt);

        cold_brew_float.addActionListener(new ActionListener() { // 콜드 브루 플로트 버튼 눌렀을 때
            @Override
            public void actionPerformed(ActionEvent e) {
                Option cold_brew_float_option=new Option(menu,true);
                cold_brew_float_option.setVisible(true);

                if (cold_brew_float_option.getBack()) // 옵션에서 뒤로 가기 눌렀다면
                {
                    cold_brew_float_option.setTopping_price(0); // 토핑 가격 0
                    cold_brew_float_option.setBack(false); // 뒤로 가기 변수 초기화
                }

                else
                {
                    cold_brew_float_cnt++; // 커피 개수 증가
                    setPricePlus(3000+cold_brew_float_option.getTopping_price()); // 가격 추가
                    setName_cnt(getName_cnt()+"\n"+"콜드 브루 플로트 (+3000원) "+cold_brew_float_cnt+"개"); // 텍스트 표시
                    if (cold_brew_float_option.getTemperature()) setName_cnt(getName_cnt()+" 아이스 (+500원)");
                    else setName_cnt(getName_cnt()+" 핫 (+0원)");
                    if (cold_brew_float_option.getVanilla()) setName_cnt(getName_cnt()+" 바닐라 시럽 토핑 (+500원)");
                    if (cold_brew_float_option.getHazelnut()) setName_cnt(getName_cnt()+" 헤이즐넛 시럽 토핑 (+500원)");
                    sum.setText(getPrice()+"원");
                }
            }
        });

        cold_brew_malt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Option cold_brew_malt_option=new Option(menu, true);
                cold_brew_malt_option.setVisible(true);

                if (cold_brew_malt_option.getBack())
                {
                    cold_brew_malt_option.setTopping_price(0);
                    cold_brew_malt_option.setBack(false);
                }

                else
                {
                    cold_brew_malt_cnt++;
                    setPricePlus(3500+cold_brew_malt_option.getTopping_price());
                    setName_cnt(getName_cnt()+"\n"+"콜드 브루 몰트 (+3500원) "+cold_brew_malt_cnt+"개"); // 텍스트 표시
                    if (cold_brew_malt_option.getTemperature()) setName_cnt(getName_cnt()+" 아이스 (+500원)");
                    else setName_cnt(getName_cnt()+" 핫 (+0원)");
                    if (cold_brew_malt_option.getVanilla()) setName_cnt(getName_cnt()+" 바닐라 시럽 토핑 (+500원)");
                    if (cold_brew_malt_option.getHazelnut()) setName_cnt(getName_cnt()+" 헤이즐넛 시럽 토핑 (+500원)");
                    sum.setText(getPrice()+"원");
                }
            }
        });
    }
}

class To_Go_Menu extends Menu // 포장 메뉴
{
    int vanilla_bean_latte_cnt=0;
    int ice_vanilla_bean_latte_cnt=0;

    ImageIcon vanilla_bean_latte_img=new ImageIcon("./image/vanilla_bean_latte.PNG");
    ImageIcon ice_vanilla_bean_latte_img=new ImageIcon("./image/ice_vanilla_bean_latte.PNG");

    public To_Go_Menu(Frame parent, boolean modal)
    {
        super(parent, modal);

        Menu menu=this;

        JButton vanilla_bean_latte=new JButton(vanilla_bean_latte_img);
        JButton ice_vanilla_bean_latte=new JButton(ice_vanilla_bean_latte_img);

        panel_menu_best.add(vanilla_bean_latte);
        panel_menu_best.add(ice_vanilla_bean_latte);

        vanilla_bean_latte.addActionListener(new ActionListener() { // 바닐라 빈 라떼 버튼
            @Override
            public void actionPerformed(ActionEvent e) {
                Option vanilla_bean_latte_option=new Option(menu,true);
                vanilla_bean_latte_option.setVisible(true);

                if (vanilla_bean_latte_option.getBack())
                {
                    vanilla_bean_latte_option.setTopping_price(0);
                    vanilla_bean_latte_option.setBack(false);
                }

                else
                {
                    vanilla_bean_latte_cnt++;
                    setPricePlus(3000+vanilla_bean_latte_option.getTopping_price());
                    setName_cnt(getName_cnt()+"\n"+"바닐라 빈 라떼 (+3000원) "+vanilla_bean_latte_cnt+"개"); // 텍스트 표시
                    if (vanilla_bean_latte_option.getTemperature()) setName_cnt(getName_cnt()+" 아이스 (+500원)");
                    else setName_cnt(getName_cnt()+" 핫 (+0원)");
                    if (vanilla_bean_latte_option.getVanilla()) setName_cnt(getName_cnt()+" 바닐라 시럽 토핑 (+500원)");
                    if (vanilla_bean_latte_option.getHazelnut()) setName_cnt(getName_cnt()+" 헤이즐넛 시럽 토핑 (+500원)");
                    sum.setText(getPrice()+"원");
                }
            }
        });

        ice_vanilla_bean_latte.addActionListener(new ActionListener() { // 아이스 바닐라 빈 라떼 버튼
            @Override
            public void actionPerformed(ActionEvent e) {
                Option ice_vanilla_bean_latte_option=new Option(menu, true);
                ice_vanilla_bean_latte_option.setVisible(true);

                if (ice_vanilla_bean_latte_option.getBack())
                {
                    ice_vanilla_bean_latte_option.setTopping_price(0);
                    ice_vanilla_bean_latte_option.setBack(false);
                }

                else
                {
                    ice_vanilla_bean_latte_cnt++;
                    setPricePlus(3500+ice_vanilla_bean_latte_option.getTopping_price());
                    setName_cnt(getName_cnt()+"\n"+"아이스 바닐라 빈 라떼 (+3500원) "+ice_vanilla_bean_latte_cnt+"개"); // 텍스트 표시
                    if (ice_vanilla_bean_latte_option.getTemperature()) setName_cnt(getName_cnt()+" 아이스 (+500원)");
                    else setName_cnt(getName_cnt()+" 핫 (+0원)");
                    if (ice_vanilla_bean_latte_option.getVanilla()) setName_cnt(getName_cnt()+" 바닐라 시럽 토핑 (+500원)");
                    if (ice_vanilla_bean_latte_option.getHazelnut()) setName_cnt(getName_cnt()+" 헤이즐넛 시럽 토핑 (+500원)");
                    sum.setText(getPrice()+"원");
                }
            }
        });
    }
}

class Option extends JDialog // 옵션
{
    private int topping_price=0;
    private boolean temperature=false;
    private boolean vanilla=false;
    private boolean hazelnut=false;
    private boolean back=false;

    public Option(Dialog parent, boolean modal)
    {
        super(parent,modal);
        setTitle("옵션");
        setBounds(100,100,500,500);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel temperature=new JLabel("HOT / ICE"); // 온도 선택

        JRadioButton hot=new JRadioButton("HOT");
        JRadioButton ice=new JRadioButton("ICE");
        ButtonGroup group=new ButtonGroup();

        temperature.setBounds(30,120,100,20);
        hot.setBounds(150,120,100,20);
        ice.setBounds(270,120,100,20);

        group.add(hot);
        group.add(ice);

        add(temperature);

        hot.setSelected(true);

        add(hot);
        add(ice);

        JLabel topping=new JLabel("토핑"); // 토핑 선택

        JCheckBox vanilla=new JCheckBox("바닐라 시럽 토핑",false);
        JCheckBox hazelnut=new JCheckBox("헤이즐넛 시럽 토핑", false);

        topping.setBounds(30,220,50,20);
        vanilla.setBounds(90,220,150,20);
        hazelnut.setBounds(270,220,150,20);

        add(topping);

        add(vanilla);
        add(hazelnut);

        JButton next=new JButton("다음");
        JButton back=new JButton("뒤로 가기");

        add(next);
        add(back);

        next.setBounds(170,300,60,20);
        back.setBounds(240,300,100,20);

        class HotListener implements ItemListener // 핫 라디오 버튼
        {

            @Override
            public void itemStateChanged(ItemEvent e) {
                switch (e.getStateChange())
                {
                    case ItemEvent.SELECTED -> {
                        setTemperature(false);
                        setTopping_pricePlus(-500);
                        break;
                    }
                }
            }
        }

        class IceListener implements ItemListener // 아이스 라디오 버튼
        {

            @Override
            public void itemStateChanged(ItemEvent e) {
                switch (e.getStateChange())
                {
                    case ItemEvent.SELECTED -> {
                        setTemperature(true);
                        setTopping_pricePlus(500);
                        break;
                    }
                }
            }
        }

        class VanillaListener implements ItemListener // 바닐라 체크 박스
        {

            @Override
            public void itemStateChanged(ItemEvent e) {
                switch (e.getStateChange())
                {
                    case ItemEvent.SELECTED -> {
                        setVanilla(true);
                        setTopping_pricePlus(500);
                        break;
                    }
                    case ItemEvent.DESELECTED -> {
                        setVanilla(false);
                        setTopping_pricePlus(-500);
                        break;
                    }
                }
            }
        }

        class HazelnutListener implements ItemListener // 헤이즐넛 체크 박스
        {

            @Override
            public void itemStateChanged(ItemEvent e) {
                switch (e.getStateChange())
                {
                    case ItemEvent.SELECTED -> {
                        setHazelnut(true);
                        setTopping_pricePlus(500);
                        break;
                    }
                    case ItemEvent.DESELECTED -> {
                        setHazelnut(false);
                        setTopping_pricePlus(-500);
                        break;
                    }
                }
            }
        }

        HotListener hotListener=new HotListener();
        IceListener iceListener=new IceListener();
        VanillaListener vanillaListener=new VanillaListener();
        HazelnutListener hazelnutListener=new HazelnutListener();

        hot.addItemListener(hotListener);
        ice.addItemListener(iceListener);
        vanilla.addItemListener(vanillaListener);
        hazelnut.addItemListener(hazelnutListener);

        next.addActionListener(new ActionListener() { // 다음 버튼 눌렀을 때
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        back.addActionListener(new ActionListener() { //뒤로 가기 버튼 눌렀을 때
            @Override
            public void actionPerformed(ActionEvent e) {
                setTopping_price(0);
                setBack(true);
                setVisible(false);
            }
        });
    }

    public int getTopping_price()
    {
        return topping_price;
    }

    public void setTopping_price(int price)
    {
        this.topping_price=price;
    }

    public void setTopping_pricePlus(int price)
    {
        this.topping_price+=price;
    }

    public boolean getTemperature()
    {
        return temperature;
    }

    public void setTemperature(boolean bool)
    {
        this.temperature=bool;
    }

    public boolean getVanilla()
    {
        return vanilla;
    }

    public void setVanilla(boolean bool)
    {
        this.vanilla=bool;
    }

    public boolean getHazelnut()
    {
        return hazelnut;
    }

    public void setHazelnut(boolean bool)
    {
        this.hazelnut=bool;
    }

    public boolean getBack()
    {
        return back;
    }

    public void setBack(boolean bool)
    {
        this.back=bool;
    }
}

class Payment extends JDialog
{
    public Payment(Dialog parent, boolean modal)
    {
        super(parent,modal);

    }
}