package Dto;

/**
 *
 * @author jlc
 */
public class UserDto extends EntityDto<Integer> {

    
    private final String name;

    public UserDto(String name) {
        this.name = name;
    }


    
    public String getName() {
        return name;
    }



}
