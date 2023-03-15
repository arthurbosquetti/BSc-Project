package misc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class TestingSite {
	
	public static void main(String[] args) {
		
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
		String str = StringUtils.join(list, ", "); // You can use any delimiter
		System.out.println(str);  // Output: 1, 2, 3, 4, 5, 6, 7
		
		BadSubClass c1 = new BadSubClass();
		c1.setField1(10);
		BadSubClass c2 = new BadSubClass();
		c2.setField1(10);
		System.out.println(c1.equals(c2));
		
		
	}
	
}


class BaseClass {
    private int field1;
    
    public void setField1(int field1) {
    	this.field1 = field1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BaseClass) {
            return field1 == ((BaseClass) obj).field1;
        }
        return false;
    }
}

class BadSubClass extends BaseClass {
}