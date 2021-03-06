package com.coveros.training;

import com.coveros.training.domainobjects.LibraryActionResults;
import com.coveros.training.persistence.LibraryUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

public class LibraryRegisterBookServletTests {

    private HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    private HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    private LibraryRegisterBookServlet libraryRegisterBookServlet;
    private final RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);


    @Before
    public void before() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        libraryRegisterBookServlet = spy(new LibraryRegisterBookServlet());
        LibraryRegisterBookServlet.libraryUtils = Mockito.mock(LibraryUtils.class);
    }

    @Test
    public void testHappyPathPost() {
        when(request.getRequestDispatcher(ServletUtils.RESULT_JSP)).thenReturn(requestDispatcher);
        when(request.getParameter("book")).thenReturn("The DevOps Handbook");
        when(LibraryRegisterBookServlet.libraryUtils.registerBook(Mockito.anyString()))
                .thenReturn(LibraryActionResults.SUCCESS);

        // do the post
        libraryRegisterBookServlet.doPost(request, response);

        // verify that the correct redirect was chosen.
        verify(request).getRequestDispatcher(ServletUtils.RESULT_JSP);
    }

    /**
     * If they pass in an empty string, it should return a message
     * indicating that.
     */
    @Test
    public void testEmptyString() {
        when(request.getRequestDispatcher(ServletUtils.RESULT_JSP)).thenReturn(requestDispatcher);
        String emptyString = "";
        when(request.getParameter("book")).thenReturn(emptyString);

        // do the post
        libraryRegisterBookServlet.doPost(request, response);

        // verify that the missing book title was handled
        Mockito.verify(request).setAttribute("result", "NO_BOOK_TITLE_PROVIDED");
    }
}
