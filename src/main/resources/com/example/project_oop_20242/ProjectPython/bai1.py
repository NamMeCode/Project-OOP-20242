
def f(x):
    return x**2-3*x+5

def search(a,b,e):
    step=0
    while  abs(b-a)>=2*e:
        x1=a+(b-a)/2-e/2
        x2=a+(b-a)/2+e/2
        if f(x1)<f(x2):
            b=x2
        elif f(x1)>f(x2):
            a=x1
        else:
            a=x1
            b=x2
        step+=1
    print(step)
    return f((b+a)/2)
print(search(1,4,0.01))
