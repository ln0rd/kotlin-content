package com.leonardobufalo.tour.advice

import com.fasterxml.jackson.core.JsonParseException
import com.leonardobufalo.tour.exception.PromocaoNotFoundException
import com.leonardobufalo.tour.model.ErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class ErrorHandler {

    @ExceptionHandler(JsonParseException::class)
    fun JsonParseExceptionHandler(
            servletRequest: HttpServletResponse,
            servletResponse: HttpServletResponse,
            exception: Exception): ResponseEntity<ErrorMessage> {
        return ResponseEntity(ErrorMessage(
                error = "Json formater error",
                message = exception.message ?: "Invalid Json"), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(PromocaoNotFoundException::class)
    fun PromocaoNotFoundExceptionHandler(servletRequest: HttpServletResponse,
                                         servletResponse: HttpServletResponse,
                                         exception: Exception) : ResponseEntity<ErrorMessage> {
        return ResponseEntity(ErrorMessage("Promoção not found", exception.message !!), HttpStatus.NOT_FOUND)
    }
}