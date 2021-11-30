insert into quotes (isin, bid, ask, elvl)
values ('US1234567890', 101.1, 101.3, 102.1)
    ON CONFLICT (isin)
    do update
               set bid = excluded.bid,
               ask = excluded.ask,
               elvl = (case
               when quotes.elvl < excluded.bid then excluded.bid
               when quotes.elvl > excluded.ask then excluded.ask
               when excluded.bid is null then excluded.ask
               else excluded.bid
               end)