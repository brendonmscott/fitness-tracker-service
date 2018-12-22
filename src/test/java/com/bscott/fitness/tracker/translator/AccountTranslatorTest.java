// FIXME
//package com.bscott.fitness.tracker.translator;
//
//import com.bscott.fitness.tracker.Constants;
//import com.bscott.fitness.tracker.dto.AccountDto;
//import com.bscott.fitness.tracker.dto.UserDto;
//import com.bscott.fitness.tracker.model.Account;
//import com.bscott.fitness.tracker.model.User;
//import org.hamcrest.CoreMatchers;
//import org.hamcrest.Matchers;
//import org.hamcrest.beans.HasPropertyWithValue;
//import org.joda.time.LocalDate;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertThat;
//import static org.junit.Assert.assertTrue;
//
//public class AccountTranslatorTest {
//
//    private AccountTranslator accountTranslator = new AccountTranslator();
//    private UserDto userDto, friendDto;
//    private Account account;
//    private AccountDto accountDto;
//    private List<Account> accountList;
//    private User user, friend;
//
//    @Before
//    public void setup(){
//
//        user = new User();
//        user.setFirstName("Homer");
//        user.setLastName("Simpson");
//        user.setBirthDate(new LocalDate("1975-12-19"));
//        user.getRoles().add(Constants.ADMIN_USER);
//
//        friend = new User();
//        friend.setFirstName("Mo");
//        friend.setLastName("Syzlak");
//        friend.setBirthDate(new LocalDate("1975-1-13"));
//        friend.getRoles().add(Constants.ADMIN_USER);
//
//        account = new Account();
//        account.setId("583a9be33004dfd16b956697");
//        account.setOwner(user);
//        account.getFriends().add(friend);
//
//        userDto = new UserDto();
//        userDto.setFirstName("Homer");
//        userDto.setLastName("Simpson");
//        userDto.setBirthDate("12/19/1975");
//        userDto.getRoles().add(Constants.ADMIN_USER);
//
//        accountList = new ArrayList<>();
//        accountList.add(account);
//
//        friendDto = new UserDto();
//        friendDto.setFirstName("Mo");
//        friendDto.setLastName("Syzlak");
//        friendDto.setBirthDate("1/13/1962");
//        friendDto.getRoles().add(Constants.ADMIN_USER);
//
//        accountDto = new AccountDto();
//        accountDto.setId("583a9be33004dfd16b956697");
//        accountDto.setOwner(userDto);
//        accountDto.getFriends().add(friendDto);
//    }
//
//    @Test
//    public void testToDto_NullEntity(){
//
//        AccountDto accountDto = accountTranslator.toDto(null);
//        assertNull(accountDto);
//    }
//
//    @Test
//    public void testToDto(){
//
//        AccountDto accountDto = accountTranslator.toDto(account);
//        assertEquals(account.getId(), accountDto.getId());
//        assertEquals(account.getOwner().getFirstName(), accountDto.getOwner().getFirstName());
//        assertThat(accountDto.getFriends(), Matchers.hasSize(1));
//        assertThat(accountDto.getFriends(), Matchers.contains(
//                HasPropertyWithValue.hasProperty("firstName", CoreMatchers.is("Mo")))
//        );
//    }
//
//    @Test
//    public void testToDtos_NullUsers(){
//
//        List<AccountDto> accountDtos = accountTranslator.toDtos(null);
//        assertNotNull(accountDtos);
//        assertTrue(accountDtos.isEmpty());
//    }
//
//    @Test
//    public void testToDtos(){
//
//        List<AccountDto> accountDtos = accountTranslator.toDtos(accountList);
//        assertThat(accountDtos, Matchers.hasSize(1));
//        assertThat(accountDtos.get(0).getFriends(), Matchers.contains(
//                HasPropertyWithValue.hasProperty("firstName", CoreMatchers.is("Mo"))));
//    }
//
//    @Test
//    public void testToEntity_NullDto(){
//
//        Account account = accountTranslator.toEntity(null);
//        assertNull(account);
//    }
//
//    @Test
//    public void testToEntity(){
//
//        Account account = accountTranslator.toEntity(accountDto);
//        assertEquals(accountDto.getId(), account.getId());
//        assertEquals(accountDto.getOwner().getFirstName(), account.getOwner().getFirstName());
//        assertThat(account.getFriends(), Matchers.hasSize(1));
//        assertThat(account.getFriends(), Matchers.contains(
//                HasPropertyWithValue.hasProperty("firstName", CoreMatchers.is("Mo"))));
//    }
//}
