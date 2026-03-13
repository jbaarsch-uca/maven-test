package com.baarsch_bytes.Library;

import com.baarsch_bytes.Library.EmailProvider;
import com.baarsch_bytes.Exceptions.DatabaseFailureException;
import com.baarsch_bytes.Exceptions.EmailFailureException;
import com.baarsch_bytes.Library.LibraryService;
import com.baarsch_bytes.Library.ResourceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import java.util.UUID;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTest {

    @Mock
    ResourceRepository mockRR;
    @Mock
    EmailProvider mockEP;



    @Test
    public void testCheckoutResource() throws Exception{

        UUID resourceID = UUID.randomUUID();
        String memberEmail = "tester@test.test";
        String message = "Resource ID: " + resourceID + " checked out.";

        when(mockRR.isResourceAvailable(resourceID)).thenReturn(true);
        when(mockEP.sendEmail(memberEmail, message)).thenReturn(true);
        when(mockRR.updateStatus(resourceID, false)).thenReturn(true);
        LibraryService service = new LibraryService(mockEP, mockRR);

        boolean result = service.checkoutResource(resourceID, memberEmail);

        assertEquals(true, result, "Checkout for " + resourceID + " failed.");
    }

    @Test
    public void testNullUUID() throws Exception{

        UUID resourceID = null;
        String memberEmail = "tester@test.test";
        String message = "Resource ID: " + resourceID + " checked out.";

        LibraryService service = new LibraryService(mockEP, mockRR);

        boolean result = service.checkoutResource(resourceID, memberEmail);

        assertEquals(false, result, "Checkout for null resource succeeded.");
    }

    @Test
    public void testResourceNotAvailable() throws Exception{

        UUID resourceID = UUID.randomUUID();
        String memberEmail = "tester@test.test";
        String member2Email = "tester2@test.test";
        String message = "Resource ID: " + resourceID + " checked out.";

        when(mockRR.isResourceAvailable(resourceID)).thenReturn(false);
        //when(mockEP.sendEmail(memberEmail, message)).thenReturn(false);
        //when(mockEP.sendEmail(member2Email, message)).thenReturn(true);


        LibraryService service = new LibraryService(mockEP, mockRR);

        boolean result = service.checkoutResource(resourceID, memberEmail);
        boolean result2 = service.checkoutResource(resourceID, member2Email);

        assertEquals(false, result, "Checkout for unavailable resource succeeded.");
    }

    //@Test
    public void testEmailException() throws Exception {

        UUID resourceID = UUID.randomUUID();
        String memberEmail = "tester@test.test";
        String message = "Resource ID: " + resourceID + " checked out.";

        when(mockRR.isResourceAvailable(resourceID)).thenReturn(false);
        when(mockRR.updateStatus(resourceID, false)).thenReturn(false);
        when(mockEP.sendEmail(memberEmail, message)).thenReturn(false);

        LibraryService service = new LibraryService(mockEP, mockRR);


        Class<? extends Throwable> exceptionClass =
                (Class<? extends Throwable>) Class
                        .forName("com.baarsch_bytes.Exceptions.EmailFailureException");
        assertThrows(exceptionClass, () -> {
            service.checkoutResource(resourceID, memberEmail);
        }, "Exception test case failed");
    }

    @Test
    public void testDatabaseException() throws Exception {

        UUID resourceID = UUID.randomUUID();
        String memberEmail = "tester@test.test";
        String message = "Resource ID: " + resourceID + " checked out.";

        when(mockRR.isResourceAvailable(resourceID)).thenReturn(true);
        when(mockRR.updateStatus(resourceID, false)).thenReturn(false);

        LibraryService service = new LibraryService(mockEP, mockRR);


        Class<? extends Throwable> exceptionClass =
                (Class<? extends Throwable>) Class
                        .forName("com.baarsch_bytes.Exceptions.DatabaseFailureException");
        assertThrows(exceptionClass, () -> {
            service.checkoutResource(resourceID, memberEmail);
        }, "Exception test case failed");
    }

}