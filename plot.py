import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
from matplotlib.dates import DateFormatter
from matplotlib.ticker import AutoLocator


plt.style.use('fivethirtyeight')


fig, ax = plt.subplots()
def animate(i):
    data = pd.read_csv('data.csv',names=["price","ask","bid","date"])
    x = pd.DatetimeIndex(data['date'])
    y1 = pd.to_numeric(data['price'])
    y2 = pd.to_numeric(data['ask'])
    y3 = pd.to_numeric(data["bid"])



    plt.cla()

    ax.plot(x, y1, color="blue", label="Price")
    ax.plot(x, y2, color="red", label="Ask Price")
    ax.fill_between(x, y1, y2, color="red", alpha=0.2)
    ax.plot(x, y3, color="green", label="Bid Price")
    ax.fill_between(x, y3, y1, color="green", alpha=0.2)
    date_format = DateFormatter("%H:%M:%S")
    ax.xaxis.set_major_formatter(date_format)
    ax.xaxis_date()


    fig.suptitle("USD/JPY Exchange Rate")
    plt.xticks(rotation=45)
    plt.legend(loc='upper left')
    plt.tight_layout()


ani = FuncAnimation(plt.gcf(), animate, interval=1000)

plt.tight_layout()
plt.show()