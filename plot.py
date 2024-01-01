import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation

plt.style.use('fivethirtyeight')



def animate(i):
    data = pd.read_csv('data.csv',names=["price","ask","bid","date"])
    #x = pd.DatetimeIndex(data['date'])
    print(data.info())
    y1 = pd.to_numeric(data['price'])
    y2 = pd.to_numeric(data['ask'])
    y3 = pd.to_numeric(data["bid"])

    plt.cla()

    plt.plot(y1, y1, label='Channel 1')
    plt.plot(y2, y2, label='Channel 2')
    plt.plot(y3, y3, label="Channel 3")

    plt.legend(loc='upper left')
    plt.tight_layout()


ani = FuncAnimation(plt.gcf(), animate, interval=1000)

plt.tight_layout()
plt.show()