package com.coveros.training;

import com.coveros.training.domainobjects.Book;
import com.coveros.training.persistence.LibraryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@MultipartConfig
@WebServlet(name = "LibraryBookListAvailableSearch", urlPatterns = {"/listavailable"}, loadOnStartup = 1)
public class LibraryBookListAvailableServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(LibraryBookListAvailableServlet.class);
    public static final String RESULT = "result";
    static LibraryUtils libraryUtils = new LibraryUtils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        final List<Book> books = libraryUtils.listAvailableBooks();
        logger.info("Received request for all available books");
        String result;
        if (books.isEmpty()) {
            result = "No books exist in the database";
        } else {
            final String allBooks = books.stream().map(Book::toOutputString).collect(Collectors.joining(","));
            result = "[" + allBooks + "]";
        }
        request.setAttribute(RESULT, result);

        ServletUtils.forwardToRestfulResult(request, response, logger);
    }


}
