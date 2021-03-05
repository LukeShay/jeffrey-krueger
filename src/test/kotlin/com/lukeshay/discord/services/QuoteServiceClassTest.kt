package com.lukeshay.discord.services

import com.lukeshay.discord.SpringTestBase
import com.lukeshay.discord.entities.Quote
import com.lukeshay.discord.repositories.QuoteRepository
import com.lukeshay.discord.services.impl.QuoteServiceImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired

class QuoteServiceClassTest @Autowired constructor(
    private val quoteService: QuoteService,
    private val quoteRepository: QuoteRepository,
    private val snowflakeService: SnowflakeService
) :
    SpringTestBase() {
    private lateinit var quote: Quote
    private var guildId: Long = 0

    @BeforeEach
    fun setUp() {
        guildId = snowflakeService.getSnowflakeId()
        quote = Quote(
            id = snowflakeService.getSnowflakeId(),
            guildId = guildId,
            author = "Jeffery Krueger",
            quote = "Hi my name Jeff",
            date = "March 4, 2021"
        )
        quote = quoteRepository.save(quote)
    }

    @AfterEach
    fun tearDown() {
        quoteRepository.deleteById(quote.id)
    }

    @Test
    fun `findOne returns a Quote`() {
        val foundQuote = quoteService.findOne(guildId) ?: Assertions.fail()

        Assertions.assertEquals(quote.id, foundQuote.id)
        Assertions.assertEquals(quote.guildId, foundQuote.guildId)
        Assertions.assertEquals(quote.author, foundQuote.author)
        Assertions.assertEquals(quote.quote, foundQuote.quote)
        Assertions.assertEquals(quote.date, foundQuote.date)
        Assertions.assertEquals(quote.lastModifiedDate, foundQuote.lastModifiedDate)
        Assertions.assertEquals(quote.createdDate, foundQuote.createdDate)
    }

    @Test
    fun `findOne returns null`() {
        val foundQuote = quoteService.findOne(0)

        Assertions.assertNull(foundQuote)
    }

    @Test
    fun `findOne returns null on exception`() {
        val quoteRepositoryMock = Mockito.mock(QuoteRepository::class.java)
        val quoteServiceWithMock = QuoteServiceImpl(quoteRepositoryMock)

        Mockito.`when`(quoteRepositoryMock.findAllByGuildId(guildId))
            .thenThrow(RuntimeException("this is an exception"))

        Assertions.assertNull(quoteServiceWithMock.findOne(guildId))
    }
}
